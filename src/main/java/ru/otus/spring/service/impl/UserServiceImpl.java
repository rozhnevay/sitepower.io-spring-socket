package ru.otus.spring.service.impl;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.UserInfoDto;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.UserInfo;
import ru.otus.spring.repository.TenantRepository;
import ru.otus.spring.repository.UserRepository;
import ru.otus.spring.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  public static final String USER_NOT_FOUND = "User not found";
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TenantRepository tenantRepository;

  @Override
  public UserInfo getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findUserByEmailIgnoreCase(auth.getName()).orElse(null);
  }

  @Override
  public UserInfo getUserById(UUID userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
  }

  @Override
  public void createUser(UserInfoDto userInfoDto) {
    Tenant tenant = new Tenant();
    tenant = tenantRepository.save(tenant);

    UserInfo user = new UserInfo();
    user.setEmail(userInfoDto.getEmail());
    user.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
    user.setFullName(userInfoDto.getFullName());
    user.setTenant(tenant);
    user.setCreatedDate(Instant.now());
    userRepository.save(user);
    tenantRepository.save(tenant);
  }
}

