package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.AuthController;
import ru.otus.spring.dto.UserInfoDto;
import ru.otus.spring.model.UserInfo;
import ru.otus.spring.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

  private final UserService userService;

  @Override
  public UserInfo login() {
    return getCurrentUser();
  }

  @Override
  public UserInfo getCurrentUser() {
    return userService.getCurrentUser();
  }

  @Override
  public void signup(UserInfoDto userInfoDto) {
     userService.createUser(userInfoDto);
  }
}
