package ru.otus.spring.service;

import java.util.ArrayList;
import java.util.UUID;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.Dialog;

public interface DialogService {

  ArrayList<MessageDto> getDialogMessages(UUID dialogId);

  DialogDto createDialog(DialogDto dialogDto);
}
