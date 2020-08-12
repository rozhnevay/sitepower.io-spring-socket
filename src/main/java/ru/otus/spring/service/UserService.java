package ru.otus.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.otus.spring.model.UserInfo;

import java.util.List;

public interface UserService {
  UserInfo getCurrentUser();
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
