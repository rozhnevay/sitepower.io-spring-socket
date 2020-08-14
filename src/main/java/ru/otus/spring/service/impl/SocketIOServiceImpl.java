package ru.otus.spring.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import java.util.ArrayList;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.UserInfo;
import ru.otus.spring.service.DialogService;
import ru.otus.spring.service.SocketIOService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.otus.spring.service.UserService;
import ru.otus.spring.service.WidgetService;

@Service
@Slf4j
public class SocketIOServiceImpl implements SocketIOService {
  /**
   * Store connected clients
   */
  private static Map<Dialog, SocketIOClient> clientMap = new ConcurrentHashMap<>();
  private static Map<Tenant, ArrayList<SocketIOClient>> tenantMap = new ConcurrentHashMap<>();
  /**
   * Custom Event`push_data_event` for service side to client communication
   */
  private static final String SEND_CLIENT_DATA_EVENT = "receive_client";
  private static final String SEND_USER_DATA_EVENT = "receive_user";
  private static final String RECEIVE_CLIENT_DATA_EVENT = "receive_client";
  private static final String RECEIVE_USER_DATA_EVENT = "receive_user";

  @Autowired
  private SocketIOServer socketIOServer;

  @Autowired
  private WidgetService widgetService;

  @Autowired
  private DialogService dialogService;

  @Autowired
  private UserService userService;
  /**
   * Spring IoC After the container is created, start after loading the SocketIOServiceImpl Bean
   */
  @PostConstruct
  private void autoStartup() {
    start();
  }

  /**
   * Spring IoC Container closes before destroying SocketIOServiceImpl Bean to avoid restarting project service port occupancy
   */
  @PreDestroy
  private void autoStop() {
    stop();
  }

  @Override
  public void start() {
    // Listen for client connections
    socketIOServer.addConnectListener(client -> {
      log.info("************ DialogId: " + getDialogIdByClient(client) + " UserId: " + getUserIdByClient(client) + " Connected ************");
      client.sendEvent("connected", "You're connected successfully...");
      String userId = getUserIdByClient(client);
      String dialogId = getDialogIdByClient(client);

      if (dialogId != null) {
        Dialog dialog = dialogService.getDialogById(UUID.fromString(dialogId));
        clientMap.put(dialog, client);
      } else if (userId != null) {
        Tenant tenant = userService.getTenantByUserId(UUID.fromString(userId));
        ArrayList<SocketIOClient> clientList = tenantMap.get(tenant);
        if (clientList == null) {
          clientList = new ArrayList<>();
        }
        clientList.add(client);
        tenantMap.put(tenant, clientList);
      } else {
        throw new RuntimeException("Can't identified client");
      }
    });

    // Listening Client Disconnect
    socketIOServer.addDisconnectListener(client -> {
      String userId = getUserIdByClient(client);
      String dialogId = getDialogIdByClient(client);
      log.info("************ DialogId: " + getDialogIdByClient(client) + " UserId: " + getUserIdByClient(client) + " Client disconnected ************");
      if (dialogId != null) {
        Dialog dialog = dialogService.getDialogById(UUID.fromString(dialogId));
        clientMap.remove(dialog);
      } else if (userId != null) {
        Tenant tenant = userService.getTenantByUserId(UUID.fromString(userId));
        ArrayList<SocketIOClient> clientList = tenantMap.get(tenant);
        if (clientList == null) {
          clientList = new ArrayList<>();
        }
        clientList.add(client);
        tenantMap.remove(tenant);
      } else {
        throw new RuntimeException("Can't identified client");
      }
    });

    // Custom Event`client_info_event` ->Listen for client messages
    socketIOServer.addEventListener(SEND_CLIENT_DATA_EVENT, Object.class, (client, data, ackSender) -> {
      String dialogId = getDialogIdByClient(client);
      Tenant tenant = dialogService.getTenantByDialogId(UUID.fromString(dialogId));
      ArrayList<SocketIOClient> clients = tenantMap.get(tenant);
      if (clients != null) {
        clients.forEach(user -> {
          user.sendEvent(RECEIVE_USER_DATA_EVENT, data);
        });
      }
      MessageDto messageDto = (MessageDto) data;
      dialogService.addDialogMessage(UUID.fromString(dialogId), messageDto);
    });

    socketIOServer.addEventListener(SEND_USER_DATA_EVENT, Object.class, (client, data, ackSender) -> {
      String userId = getUserIdByClient(client);
    });

    socketIOServer.start();
  }

  @Override
  public void stop() {
    if (socketIOServer != null) {
      socketIOServer.stop();
      socketIOServer = null;
    }
  }


  private String getDialogIdByClient(SocketIOClient client) {
    if (client.getHandshakeData().getUrlParams().get("dialogId") != null) {
      return client.getHandshakeData().getUrlParams().get("dialogId").get(0);
    }
    return null;
  }

  private String getUserIdByClient(SocketIOClient client) {
    if (client.getHandshakeData().getUrlParams().get("userId") != null) {
      return client.getHandshakeData().getUrlParams().get("userId").get(0);
    }
    return null;
  }
}
