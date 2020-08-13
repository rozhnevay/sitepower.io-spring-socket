package ru.otus.spring.controller;


import java.util.ArrayList;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.model.UserInfo;

@RequestMapping(path = "/api-widget/dialog")
public interface DialogExternalController {
  @GetMapping("/{dialogId}/message")
  ArrayList<MessageDto> getDialogMessages(@PathVariable("dialogId") UUID dialogId);

  @PostMapping
  DialogDto createDialog(DialogDto dialogDto);
}
