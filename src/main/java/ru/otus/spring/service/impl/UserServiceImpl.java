package ru.otus.spring.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.UserInfo;
import ru.otus.spring.repository.UserRepository;
import ru.otus.spring.service.UserService;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//  public static final String ROLE_NOT_FOUND_MSG = "Роль не найдена";
//  public static final String USER_NOT_FOUND_MSG = "Пользователь не найден";
//  public static final String REQUIRED_FIELDS_EMPTY = "Не заполнены обязательные поля";
//  public static final String ERROR = "Пользователь с таким именем существует";
//  public static final String USERNAME_LENGTH_ERROR = "Имя пользователя должно быть от 8 до 20 символов";
//  public static final String USERNAME_CHARS_ERROR = "Имя пользователя содержит недопустимые символы";
//  public static final String REGEX = ".*[A-z0-9-._]";
//
  private final UserRepository userRepository;
//  private final RoleRepository roleRepository;
//  private final PasswordEncoder passwordEncoder;
//  private final PasswordService passwordService;

  @Override
  public UserInfo getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findUserByEmailIgnoreCase(auth.getName()).orElse(null);
  }

  @Override
  public Tenant getTenantByUserId(UUID userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getTenant();
  }
//
//  @Override
//  public UserInfo create(UserInfo userInfo) {
//    return saveUser(new UserInfo(), userInfo);
//  }
//
//  @Override
//  public UserInfo update(long id, UserInfo userInfo) {
//    UserInfo currentUserInfo = getUser(id);
//    return saveUser(currentUserInfo, userInfo);
//  }
//
//  private UserInfo saveUser(UserInfo currentUser, UserInfo newUser) {
//    if (StringUtils.isEmpty(newUser.getLastName())
//        || StringUtils.isEmpty(newUser.getFirstName())
//        || StringUtils.isEmpty(newUser.getLogin())
//        || StringUtils.isEmpty(newUser.getRole())) {
//      throw new CommonErrorException(REQUIRED_FIELDS_EMPTY);
//    }
//    validateUsername(newUser.getLogin());
//    currentUser.setEmail(newUser.getEmail());
//    currentUser.setAccountNonExpired(true);
//    currentUser.setAccountNonLocked(true);
//    currentUser.setEnabled(newUser.isEnabled());
//    currentUser.setCredentialsNonExpired(newUser.isCredentialsNonExpired());
//
//    currentUser.setEmail(newUser.getEmail());
//    currentUser.setFirstName(newUser.getFirstName());
//    currentUser.setLastName(newUser.getLastName());
//    currentUser.setMiddleName(newUser.getMiddleName());
//    currentUser.setFullName(newUser.getLastName() + " " + newUser.getFirstName() + " " + newUser.getMiddleName());
//    currentUser.setLogin(newUser.getLogin());
//    if (newUser.getTechUser() != null) {
//      currentUser.setTechUser(newUser.getTechUser());
//    } else {
//      currentUser.setTechUser(false);
//    }
//
//
//    if (newUser.getRole() != null) {
//      currentUser.setRole(getRole(newUser.getRole().getName()));
//    }
//    if (newUser.getPassword() != null) {
//      if (currentUser.getPassword() != null) {
//        passwordService.validateAndSavePassword(currentUser, newUser.getPassword(), false);
//      } else {
//        passwordService.validateAndSavePassword(currentUser, newUser.getPassword(), true);
//      }
//    }
//    return userRepository.save(currentUser);
//  }
//
//  @Override
//  public Page<UserInfo> getAll(Specification<UserInfo> specification, Pageable pageable) {
//    return userRepository.findAll(specification, pageable);
//  }
//
//  @Override
//  public void updatePassword(long id, PasswordRequestDto passwordRequestDto) {
//    UserInfo userInfo = getUser(id);
//    passwordService.updateUserPassword(userInfo, passwordRequestDto.getOldPassword(), passwordRequestDto.getNewPassword());
//    userInfo.setCredentialsNonExpired(true);
//    userRepository.save(userInfo);
//  }
//
//  @Override
//  public List<UserInfo> getUsersWithExpiredCredentials(Long daysToExpire) {
//    return userRepository.findUsersWithExpiredCredentials(daysToExpire);
//  }
//
//  @Override
//  public List<UserInfo> saveAll(List<UserInfo> users) {
//    return userRepository.saveAll(users);
//  }
//
//  private UserInfo getUser(long id) {
//    return userRepository
//        .findById(id)
//        .orElseThrow(() -> new CommonErrorException(USER_NOT_FOUND_MSG));
//  }
//
//  private Role getRole(String roleName) {
//    return roleRepository
//        .findByName(roleName)
//        .orElseThrow(() -> new CommonErrorException(ROLE_NOT_FOUND_MSG));
//  }
//
//  private void validateUsername(String username) {
//    if (userRepository.findUserByLoginIgnoreCase(username).isPresent()) {
//      throw new CommonErrorException(ERROR);
//    }
//    if (username.length() < 8 || username.length() > 20) {
//      throw new CommonErrorException(USERNAME_LENGTH_ERROR);
//    }
//    if (!Pattern.matches(REGEX, username)) {
//      throw new CommonErrorException(USERNAME_CHARS_ERROR);
//    }
//  }
}

