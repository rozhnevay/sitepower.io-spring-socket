package ru.otus.spring.service;

import ru.otus.spring.dto.EmployeeDto;
import ru.otus.spring.dto.UserInfoDto;
import ru.otus.spring.model.UserInfo;

import java.util.List;
import java.util.UUID;

public interface UserService {
  UserInfo getCurrentUser();

  UserInfo getUserById(UUID userId);

  void createUser(UserInfoDto userInfo);

  List<EmployeeDto> getCurrentUserEmployees();

  void putUserEmployee(EmployeeDto employeeDto);
}
