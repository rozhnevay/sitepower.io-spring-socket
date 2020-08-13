package ru.otus.spring.controller.impl;

import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.WidgetExternalController;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.dto.WidgetDto;
import ru.otus.spring.service.WidgetService;

@RestController
@RequiredArgsConstructor
public class WidgetExternalControllerImpl implements WidgetExternalController {

  private final WidgetService widgetService;

  @Override
  public WidgetDto getWidget(UUID widgetId) {
    return widgetService.getWidget(widgetId);
  }
}
