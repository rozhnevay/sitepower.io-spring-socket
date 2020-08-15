package ru.otus.spring.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DialogDto {
  private UUID dialogId;
  private UUID widgetId;
  private String className;
  private String fullName;
  private String phone;
  private String type;
  private String lastMessageBody;
  private Instant lastMessageCreated;
  private int countUnaswered;
  private Instant lastOpenDate;
}
