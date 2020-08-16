package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.DialogExternalController;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.service.DialogService;
import ru.otus.spring.service.MessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DialogExternalControllerImpl implements DialogExternalController {

  private final DialogService dialogService;

  private final MessageService messageService;

  @Override
  public List<MessageDto> getDialogMessages(UUID dialogId) {
    Dialog dialog = dialogService.getDialogById(dialogId);
    return messageService.getMessageDtos(dialog);
  }

  @Override
  public DialogDto createDialog(DialogDto dialogDto) {
    return dialogService.getOrCreateDialog(dialogDto);
  }
}
