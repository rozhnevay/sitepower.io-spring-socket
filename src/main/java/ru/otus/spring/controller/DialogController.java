package ru.otus.spring.controller;

import java.util.ArrayList;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;

@RequestMapping(path = "/api/dialog")
public interface DialogController {
  @GetMapping()
  ArrayList<DialogDto> getDialogs();

  @GetMapping("/{dialogId}/message")
  ArrayList<MessageDto> getDialogMessages(@PathVariable("dialogId") UUID dialogId);
}
