package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
