package ru.otus.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.model.UserInfo;

@RequestMapping(path = "/api/chats")
public interface ChatsController {
  @GetMapping
  UserInfo getChats();
}
