package ru.otus.spring.dto;

import java.time.Instant;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
  private UUID id;
  private Instant createdDate;
  private String body;
  private String type;
  private String link;
  private String direction;
  private String operatorName;
  private UUID recepientId;
  private UUID senderId;
}