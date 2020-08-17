package ru.otus.spring.dto;

import java.util.UUID;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
  private UUID id;
  private String createdDate;
  private String fullName;
  private String email;
  private String password;
}