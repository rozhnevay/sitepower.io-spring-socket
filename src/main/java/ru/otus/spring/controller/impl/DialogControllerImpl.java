package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.DialogController;
import ru.otus.spring.dto.CategoryDto;
import ru.otus.spring.dto.ContactDto;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.service.DialogService;
import ru.otus.spring.service.MessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

  private final DialogService dialogService;

  private final MessageService messageService;

  @Override
  public List<DialogDto> getDialogs() {
    return dialogService.getDialogsByCurrentTenant();
  }

  @Override
  public List<MessageDto> getDialogMessages(@PathVariable("dialogId") UUID dialogId) {
    Dialog dialog = dialogService.getDialogById(dialogId);
    return messageService.getMessageDtos(dialog);
  }

  @Override
  public void putDialogContacts(@PathVariable("dialogId") UUID dialogId, @RequestBody ContactDto contactDto) {
    Dialog dialog = dialogService.getDialogById(dialogId);
    dialogService.putDialogContacts(dialog, contactDto);
  }

  @Override
  public void putDialogCategory(@PathVariable("dialogId") UUID dialogId, @RequestBody CategoryDto categoryDto) {
    Dialog dialog = dialogService.getDialogById(dialogId);
    dialogService.putDialogCategory(dialog, categoryDto);
  }

  @Override
  public void sendDialogToMail(UUID dialogId) {
    Dialog dialog = dialogService.getDialogById(dialogId);
    dialogService.sendDialogToEmail(dialog);
  }
}

