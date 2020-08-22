package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.WidgetExternalController;
import ru.otus.spring.dto.WidgetDto;
import ru.otus.spring.service.WidgetService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WidgetExternalControllerImpl implements WidgetExternalController {

  private final WidgetService widgetService;

  @Override
  public WidgetDto getWidget(UUID widgetId) {
    return widgetService.getWidgetDto(widgetId);
  }
}
