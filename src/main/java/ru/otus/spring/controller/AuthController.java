package ru.otus.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.dto.UserInfoDto;
import ru.otus.spring.model.UserInfo;

@RequestMapping(path = "/api/auth")
public interface AuthController {

  @PostMapping("/login")
  UserInfo login();

  @GetMapping("/user")
  UserInfo getCurrentUser();

  @PostMapping("/signup")
  void signup(@RequestBody UserInfoDto userInfo);
}
