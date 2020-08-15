package ru.otus.spring.service;

import java.util.ArrayList;
import java.util.UUID;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Tenant;

public interface DialogService {

  ArrayList<MessageDto> getDialogMessages(UUID dialogId);

  void addDialogMessage(UUID dialogId, MessageDto messageDto);

  DialogDto createDialog(DialogDto dialogDto);

  Tenant getTenantByDialogId(UUID dialogId);

  Dialog getDialogById(UUID dialogId);

  ArrayList<DialogDto> getDialogsByCurrentTenant();
}
