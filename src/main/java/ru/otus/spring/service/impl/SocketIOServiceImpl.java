package ru.otus.spring.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

import java.util.*;

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
  private static Map<UUID, ArrayList<SocketIOClient>> tenantMap = new ConcurrentHashMap<>();
  /**
   * Custom Event`push_data_event` for service side to client communication
   */
  private static final String SEND_CLIENT_DATA_EVENT = "send_client";
  private static final String SEND_USER_DATA_EVENT = "send_user";
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

  @PostConstruct
  private void autoStartup() {
    start();
  }

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
        /* Значит это клиент из плагина */
        Dialog dialog = dialogService.getDialogById(UUID.fromString(dialogId));
        clientMap.put(dialog, client);
      } else if (userId != null) {
        /* Значит это пользователь из админки */
        Tenant tenant = userService.getTenantByUserId(UUID.fromString(userId));
        ArrayList<SocketIOClient> clientList = tenantMap.get(tenant.getId());
        if (clientList == null) {
          clientList = new ArrayList<>();
        }
        clientList.add(client);
        tenantMap.put(tenant.getId(), clientList);
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
        ArrayList<SocketIOClient> clientList = tenantMap.get(tenant.getId());
        if (clientList == null) {
          clientList = new ArrayList<>();
        }
        clientList.add(client);
        tenantMap.remove(tenant.getId());
      } else {
        throw new RuntimeException("Can't identified client");
      }
    });

    // Custom Event`client_info_event` ->Listen for client messages
    socketIOServer.addEventListener(SEND_CLIENT_DATA_EVENT, Object.class, (client, data, ackSender) -> {
      String dialogId = getDialogIdByClient(client);
      Tenant tenant = dialogService.getTenantByDialogId(UUID.fromString(dialogId));


      MessageDto messageDto = new MessageDto();
      LinkedHashMap<String, String> dataMap = (LinkedHashMap<String, String>) data;
      messageDto.setBody(dataMap.get("body"));
      messageDto.setType(dataMap.get("type"));
      messageDto.setLink(dataMap.get("link"));
      messageDto.setDirection("to_user");
      messageDto.setCreatedDate(Instant.now());
      messageDto.setSenderId(UUID.fromString(dialogId));
      dialogService.addDialogMessage(UUID.fromString(dialogId), messageDto);

      ArrayList<SocketIOClient> clients = tenantMap.get(tenant.getId());
      if (clients != null) {
        clients.forEach(user -> {
          user.sendEvent(RECEIVE_USER_DATA_EVENT, messageDto);
        });
      }
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
