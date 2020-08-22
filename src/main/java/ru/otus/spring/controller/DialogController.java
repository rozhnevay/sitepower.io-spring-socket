package ru.otus.spring.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.CategoryDto;
import ru.otus.spring.dto.ContactDto;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.MessageDto;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/api/dialog")
public interface DialogController {
  @GetMapping()
  List<DialogDto> getDialogs();

  @GetMapping("/{dialogId}/message")
  List<MessageDto> getDialogMessages(@PathVariable("dialogId") UUID dialogId);

  @PostMapping("/{dialogId}/contact")
  void putDialogContacts(@PathVariable("dialogId") UUID dialogId, @RequestBody ContactDto contactDto);

  @PostMapping("/{dialogId}/category")
  void putDialogCategory(@PathVariable("dialogId") UUID dialogId, @RequestBody CategoryDto categoryDto);

  @PostMapping("/{dialogId}/send")
  void sendDialogToMail(@PathVariable("dialogId") UUID dialogId);
}
