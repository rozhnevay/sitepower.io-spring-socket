package ru.otus.spring.service;

import java.util.UUID;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.dto.WidgetDto;

public interface WidgetService {
  WidgetDto getWidget(UUID widgetId);
}
