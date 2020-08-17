package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.EmployeeDto;
import ru.otus.spring.dto.UserInfoDto;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.UserInfo;
import ru.otus.spring.repository.TenantRepository;
import ru.otus.spring.repository.UserRepository;
import ru.otus.spring.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  public static final String USER_NOT_FOUND = "User not found";
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TenantRepository tenantRepository;
  private final ModelMapper mapper;

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
    user = userRepository.save(user);
    tenant.setMainUserInfo(user);
    tenantRepository.save(tenant);
  }

  @Override
  public List<EmployeeDto> getCurrentUserEmployees() {
    List<UserInfo> userInfoList = userRepository.findAllByTenant(getCurrentUser().getTenant());
    return userInfoList.stream().map(this::mapUserToEmployeeDto).collect(Collectors.toList());
  }

  @Override
  public void putUserEmployee(EmployeeDto employeeDto) {
    userRepository.save(mapEmployeeDtoToUser(employeeDto));
  }

  private EmployeeDto mapUserToEmployeeDto(UserInfo userInfo) {
    EmployeeDto employeeDto = mapper.map(userInfo, EmployeeDto.class);
    if (userInfo.getTenant().getMainUserInfo().equals(userInfo)) {
      employeeDto.setAdmin("Y");
    } else {
      employeeDto.setAdmin("N");
    }
    employeeDto.setLogin(userInfo.getEmail());
    employeeDto.setName(userInfo.getFullName());
    return employeeDto;
  }

  private UserInfo mapEmployeeDtoToUser(EmployeeDto employeeDto) {
    UserInfo userInfo = mapper.map(employeeDto, UserInfo.class);
    userInfo.setEmail(employeeDto.getLogin());
    userInfo.setFullName(employeeDto.getName());
    userInfo.setTenant(getCurrentUser().getTenant());
    userInfo.setCreatedDate(Instant.now());
    userInfo.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
    return userInfo;
  }
}

