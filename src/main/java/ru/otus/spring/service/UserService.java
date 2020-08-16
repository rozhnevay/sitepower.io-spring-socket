package ru.otus.spring.service;

import ru.otus.spring.model.UserInfo;

import java.util.UUID;

public interface UserService {
  UserInfo getCurrentUser();

  UserInfo getUserById(UUID userId);
//
//  UserInfo create(UserInfo userInfo);
//
//  UserInfo update(long id, UserInfo userInfo);
//
//  Page<UserInfo> getAll(Specification<UserInfo> specification, Pageable pageable);
//
//  void updatePassword(long id, PasswordRequestDto passwordRequestDto);
//
//  List<UserInfo> getUsersWithExpiredCredentials(Long daysToExpire);
//
//  List<UserInfo> saveAll(List<UserInfo> users);
}
