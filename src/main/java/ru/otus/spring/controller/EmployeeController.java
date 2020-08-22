package ru.otus.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.dto.EmployeeDto;

import java.util.List;

@RequestMapping(path = "/api/employee")
public interface EmployeeController {
  @GetMapping()
  List<EmployeeDto> getEmployees();

  @PostMapping()
  void putEmployee(@RequestBody EmployeeDto employeeDto);
}
