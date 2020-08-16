package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DialogDto {
  private UUID dialogId;
  private UUID widgetId;
  private String className;
  private String fullName;
  private String email;
  private String phone;
  private String type;
  private String lastMessageBody;
  private Instant lastMessageCreated;
  private Instant createdDate;
  private int countUnaswered;
  private Instant lastOpenDate;
}
