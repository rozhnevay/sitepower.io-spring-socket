package ru.otus.spring.dto;

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
}
