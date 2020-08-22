package ru.otus.spring.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.service.DialogService;
import ru.otus.spring.service.MessageService;
import ru.otus.spring.service.SocketIOService;
import ru.otus.spring.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketIOServiceImpl implements SocketIOService {

  public static final String CAN_T_IDENTIFIED_CLIENT = "Can't identified client";

  private static final String SEND_CLIENT_DATA_EVENT = "send_client";
  private static final String SEND_USER_DATA_EVENT = "send_user";
  private static final String RECEIVE_CLIENT_DATA_EVENT = "receive_client";
  private static final String RECEIVE_USER_DATA_EVENT = "receive_user";

  private static final Map<UUID, SocketIOClient> clientMap = new ConcurrentHashMap<>();
  private static final Map<UUID, ArrayList<SocketIOClient>> tenantMap = new ConcurrentHashMap<>();

  private final SocketIOServer socketIOServer;
  private final DialogService dialogService;
  private final UserService userService;
  private final MessageService messageService;

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
    socketIOServer.addConnectListener(client -> {
      UUID userId = getUserIdByClient(client);
      UUID dialogId = getDialogIdByClient(client);

      log.info("************ DialogId: " + dialogId + " UserId: " + userId + " Connected ************");

      if (dialogId != null) { /* Значит это клиент из плагина */
        clientMap.put(dialogId, client);
      } else if (userId != null) { /* Значит это пользователь из админки */
        Tenant tenant = userService.getUserById(userId).getTenant();
        ArrayList<SocketIOClient> clientList = tenantMap.get(tenant.getId());
        if (clientList == null) {
          clientList = new ArrayList<>();
        }
        clientList.add(client);
        tenantMap.put(tenant.getId(), clientList);
      } else {
        throw new RuntimeException(CAN_T_IDENTIFIED_CLIENT);
      }
    });

    socketIOServer.addDisconnectListener(client -> {
      UUID userId = getUserIdByClient(client);
      UUID dialogId = getDialogIdByClient(client);

      log.info("************ DialogId: " + getDialogIdByClient(client) + " UserId: " + getUserIdByClient(client) + " Client disconnected ************");

      if (dialogId != null) {
        clientMap.remove(dialogId);
      } else if (userId != null) {
        Tenant tenant = userService.getUserById(userId).getTenant();
        ArrayList<SocketIOClient> clientList = tenantMap.get(tenant.getId());
        if (clientList != null) {
          clientList.remove(client);
          if (clientList.size() == 0) {
            tenantMap.remove(tenant.getId());
          }
        }
      } else {
        throw new RuntimeException(CAN_T_IDENTIFIED_CLIENT);
      }
    });

    /* отправка сообщения из плагина */
    socketIOServer.addEventListener(SEND_CLIENT_DATA_EVENT, Object.class, (client, data, ackSender) -> {
      UUID dialogId = getDialogIdByClient(client);
      Dialog dialog = dialogService.getDialogById(dialogId);
      Tenant tenant = dialog.getWidget().getTenant();

      MessageDto messageDto = messageService.addMessage(dialog, mapClientMessageToMessageDto(data, dialogId));

      ArrayList<SocketIOClient> clients = tenantMap.get(tenant.getId());
      /* Отправляем всем кто онлайн из тенанта */
      if (clients != null) {
        clients.forEach(user -> {
          user.sendEvent(RECEIVE_USER_DATA_EVENT, messageDto);
        });
      }
      /* Отправляем себе же как признак, что сообщение успешно ушло */
      client.sendEvent(RECEIVE_CLIENT_DATA_EVENT, messageDto);
    });

    socketIOServer.addEventListener(SEND_USER_DATA_EVENT, Object.class, (client, data, ackSender) -> {
      MessageDto messageDto = mapUserMessageToMessageDto(data);
      Dialog dialog = dialogService.getDialogById(messageDto.getRecepientId());
      messageDto = messageService.addMessage(dialog, messageDto);
      SocketIOClient clientSocket = clientMap.get(messageDto.getRecepientId());
      if (clientSocket != null) {
        clientSocket.sendEvent(RECEIVE_CLIENT_DATA_EVENT, messageDto);
      }
      client.sendEvent(RECEIVE_USER_DATA_EVENT, messageDto);
    });

    socketIOServer.start();
  }

  @Override
  public void stop() {
    if (socketIOServer != null) {
      socketIOServer.stop();
    }
  }

  private MessageDto mapMessageCommonAttributes(Object message) {
    MessageDto messageDto = new MessageDto();
    LinkedHashMap<String, String> dataMap = (LinkedHashMap<String, String>) message;
    messageDto.setBody(dataMap.get("body"));
    messageDto.setType(dataMap.get("type"));
    messageDto.setLink(dataMap.get("link"));
    return messageDto;
  }

  private MessageDto mapClientMessageToMessageDto(Object message, UUID dialogId) {
    MessageDto messageDto = mapMessageCommonAttributes(message);
    messageDto.setDirection("to_user");
    messageDto.setSenderId(dialogId);
    return messageDto;
  }

  private MessageDto mapUserMessageToMessageDto(Object message) {
    MessageDto messageDto = mapMessageCommonAttributes(message);
    messageDto.setDirection("from_user");
    LinkedHashMap<String, String> dataMap = (LinkedHashMap<String, String>) message;
    messageDto.setRecepientId(UUID.fromString(dataMap.get("recepientId")));
    return messageDto;
  }

  private UUID getDialogIdByClient(SocketIOClient client) {
    List<String> dialogIdList = client.getHandshakeData().getUrlParams().get("dialogId");
    if (dialogIdList != null) {
      return UUID.fromString(dialogIdList.get(0));
    }
    return null;
  }

  private UUID getUserIdByClient(SocketIOClient client) {
    List<String> userIdList = client.getHandshakeData().getUrlParams().get("userId");
    if (userIdList != null) {
      return UUID.fromString(userIdList.get(0));
    }
    return null;
  }
}
