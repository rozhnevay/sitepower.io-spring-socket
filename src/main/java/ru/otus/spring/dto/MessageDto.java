package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
  private UUID id;
  private String createdDate;
  private String body;
  private String type;
  private String link;
  private String direction;
  private String operatorName;
  private UUID recepientId;
  private UUID senderId;
}
