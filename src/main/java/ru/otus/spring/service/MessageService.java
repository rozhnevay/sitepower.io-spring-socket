package ru.otus.spring.service;

import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;

import java.util.List;

public interface MessageService {
  List<Message> getMessages(Dialog dialog);

  List<MessageDto> getMessageDtos(Dialog dialog);

  MessageDto addMessage(Dialog dialog, MessageDto messageDto);
}
