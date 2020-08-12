package ru.otus.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.model.UserInfo;

@RequestMapping(path = "/api/auth")
public interface AuthController {

  @PostMapping("/login")
  UserInfo login();

  @GetMapping("/user")
  UserInfo getCurrentUser();

  @PostMapping("/signup")
  UserInfo signup(UserInfo userInfo);
}
