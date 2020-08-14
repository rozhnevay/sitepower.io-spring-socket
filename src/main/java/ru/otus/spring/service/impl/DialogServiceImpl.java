package ru.otus.spring.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.Widget;
import ru.otus.spring.repository.DialogRepository;
import ru.otus.spring.repository.MessageRepository;
import ru.otus.spring.repository.UserRepository;
import ru.otus.spring.repository.WidgetRepository;
import ru.otus.spring.service.DialogService;

@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

  private final MessageRepository messageRepository;

  private final DialogRepository dialogRepository;

  private final WidgetRepository widgetRepository;

  @Override
  public ArrayList<MessageDto> getDialogMessages(UUID dialogId) {
    Dialog dialog = dialogRepository.findById(dialogId).orElseThrow(() -> new RuntimeException("Dialog not found")); /* TODO: в коснтанты */
    ArrayList<Message> messageArrayList = messageRepository.findAllByDialog(dialog);
    ArrayList<MessageDto> messageDtos = new ArrayList<>();

    messageArrayList.forEach(message -> {
      MessageDto messageDto = new MessageDto();
      messageDto.setBody(message.getBody());
      messageDto.setCreatedDate(message.getCreatedDate());
      messageDto.setDirection(message.getDirection());
      messageDto.setId(message.getId());
      messageDto.setLink(message.getLink());
      if (message.getUserInfo() != null) {
        messageDto.setOperatorName(message.getUserInfo().getFullName());
      }
      messageDto.setType(message.getType());
    });
    return messageDtos;
  }

  @Override
  public void addDialogMessage(UUID dialogId, MessageDto messageDto) {
    Dialog dialog = dialogRepository.findById(dialogId).orElseThrow(() -> new RuntimeException("Dialog not found"));
    Message message = new Message();
    message.setBody(messageDto.getBody());
    message.setDialog(dialog);
    message.setDirection(messageDto.getDirection());
    message.setType(messageDto.getType());
    /* TODO: доделать сохранение юзера в зависимости от direction */
    messageRepository.save(message);
  }

  @Override
  public DialogDto createDialog(DialogDto dialogDto) {
    if (dialogDto.getWidgetId() == null) {
      throw new RuntimeException("Widget not specified"); /* TODO: в коснтанты */
    }
    Dialog dialog = null;
    if (!StringUtils.isEmpty(dialogDto.getDialogId())) {
      dialog = dialogRepository.findById(dialogDto.getDialogId()).orElse(null);
    }
    if (dialog == null) {
      Widget widget = widgetRepository.findById(dialogDto.getWidgetId()).orElseThrow(() -> new RuntimeException("Widget not found")); /* TODO: в коснтанты */
      dialog = new Dialog();
      dialog.setWidget(widget);
      dialog = dialogRepository.save(dialog);
    }
    dialogDto.setDialogId(dialog.getId());
    dialogDto.setWidgetId(dialog.getWidget().getId());
    return dialogDto;
  }

  @Override
  public Tenant getTenantByDialogId(UUID dialogId) {
    Dialog dialog = dialogRepository.findById(dialogId).orElseThrow(() -> new RuntimeException("Dialog not found"));
    return  dialog.getWidget().getTenant();
  }

  @Override
  public Dialog getDialogById(UUID dialogId) {
    return dialogRepository.findById(dialogId).get();
  }
}
