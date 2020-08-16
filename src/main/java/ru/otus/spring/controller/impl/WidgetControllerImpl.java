package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.WidgetController;
import ru.otus.spring.dto.WidgetDto;
import ru.otus.spring.service.WidgetService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WidgetControllerImpl implements WidgetController {

  private final WidgetService widgetService;

  @Override
  public List<WidgetDto> getWidgets() {
    return widgetService.getWidgetDtosByCurrentUser();
  }

  @Override
  public void saveWidgetSettings(UUID widgetId, WidgetDto widgetDto) {
    widgetService.saveWidget(widgetId, widgetDto);
  }
}
