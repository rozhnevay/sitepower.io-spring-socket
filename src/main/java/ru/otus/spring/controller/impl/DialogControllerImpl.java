package ru.otus.spring.controller.impl;

import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.DialogController;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.service.DialogService;

@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

  private final DialogService dialogService;

  @Override
  public ArrayList<DialogDto> getDialogs() {
    return dialogService.getDialogsByCurrentTenant();
  }

  @Override
  public ArrayList<MessageDto> getDialogMessages(@PathVariable("dialogId") UUID dialogId) {
    return dialogService.getDialogMessages(dialogId);
  }
}
