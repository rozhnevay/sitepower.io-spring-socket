package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.EmployeeController;
import ru.otus.spring.dto.EmployeeDto;
import ru.otus.spring.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController {

  private final UserService userService;

  @Override
  public List<EmployeeDto> getEmployees() {
    return userService.getCurrentUserEmployees();
  }

  @Override
  public void putEmployee(EmployeeDto employeeDto) {
    userService.putUserEmployee(employeeDto);
  }
}
