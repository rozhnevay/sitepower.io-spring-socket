package ru.otus.spring.service;

import ru.otus.spring.dto.CategoryDto;
import ru.otus.spring.dto.ContactDto;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;

import java.util.List;
import java.util.UUID;

public interface DialogService {

  DialogDto getOrCreateDialog(DialogDto dialogDto);

  Dialog getDialogById(UUID dialogId);

  List<DialogDto> getDialogsByCurrentTenant();

  void putDialogContacts(Dialog dialog, ContactDto contactDto);

  void putDialogCategory(Dialog dialog, CategoryDto categoryDto);

  void putDialogLastMessage(Dialog dialog, Message message);

  void sendDialogToEmail(Dialog dialog);
}
