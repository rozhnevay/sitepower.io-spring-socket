package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.UserInfo;
import ru.otus.spring.repository.UserRepository;
import ru.otus.spring.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  public static final String USER_NOT_FOUND = "User not found";
  private final UserRepository userRepository;

  @Override
  public UserInfo getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findUserByEmailIgnoreCase(auth.getName()).orElse(null);
  }

  @Override
  public UserInfo getUserById(UUID userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
  }
}

