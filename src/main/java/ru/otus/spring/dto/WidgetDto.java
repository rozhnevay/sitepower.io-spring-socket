package ru.otus.spring.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WidgetDto {
  private UUID id;
  private String messagePlaceholder;
  private String label;
  private int gradient;
  private String color;
}