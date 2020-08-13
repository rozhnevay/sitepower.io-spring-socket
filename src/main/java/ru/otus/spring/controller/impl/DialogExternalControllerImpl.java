package ru.otus.spring.controller.impl;

import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.DialogExternalController;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.service.DialogService;

@RestController
@RequiredArgsConstructor
public class DialogExternalControllerImpl implements DialogExternalController {

  private final DialogService dialogService;

  @Override
  public ArrayList<MessageDto> getDialogMessages(UUID dialogId) {
    return dialogService.getDialogMessages(dialogId);
  }

  @Override
  public DialogDto createDialog(DialogDto dialogDto) {
    return dialogService.createDialog(dialogDto);
  }
}
