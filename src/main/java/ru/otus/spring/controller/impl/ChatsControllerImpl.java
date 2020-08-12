package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.AuthController;
import ru.otus.spring.controller.ChatsController;
import ru.otus.spring.model.UserInfo;
import ru.otus.spring.service.UserService;

@RestController
@RequiredArgsConstructor
public class ChatsControllerImpl implements ChatsController {

  private final UserService userService;

  @Override
  public UserInfo getChats() {
    return null;
  }
}
