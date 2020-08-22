package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;
import ru.otus.spring.repository.MessageRepository;
import ru.otus.spring.service.DialogService;
import ru.otus.spring.service.MessageService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  public static final String TO_USER = "to_user";
  private final MessageRepository messageRepository;
  private final DialogService dialogService;
  private final ModelMapper mapper;

  @Override
  public List<Message> getMessages(Dialog dialog) {
    return messageRepository.findAllByDialog(dialog);
  }

  @Override
  public List<MessageDto> getMessageDtos(Dialog dialog) {
    return getMessages(dialog).stream().map(this::mapMessageToDto).collect(Collectors.toList());
  }

  @Override
  public MessageDto addMessage(Dialog dialog, MessageDto messageDto) {
    Message message = mapDtoToMessage(messageDto);
    message.setDialog(dialog);
    message = messageRepository.save(message);

    dialogService.putDialogLastMessage(dialog, message);
    return mapMessageToDto(message);
  }

  private Message mapDtoToMessage(MessageDto messageDto) {
    Message message = mapper.map(messageDto, Message.class);
    message.setCreatedDate(Instant.now());
    return message;
  }

  private MessageDto mapMessageToDto(Message message) {
    MessageDto messageDto = mapper.map(message, MessageDto.class);
    if (message.getUserInfo() != null) {
      messageDto.setOperatorName(message.getUserInfo().getFullName());
    }
    if (message.getDirection().equals(TO_USER)) {
      messageDto.setSenderId(message.getDialog().getId());
    } else {
      messageDto.setRecepientId(message.getDialog().getId());
    }
    messageDto.setCreatedDate(message.getCreatedDate().toString());
    return messageDto;
  }
}
