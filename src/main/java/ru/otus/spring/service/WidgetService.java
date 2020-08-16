package ru.otus.spring.service;

import ru.otus.spring.dto.WidgetDto;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.Widget;

import java.util.List;
import java.util.UUID;

public interface WidgetService {

  List<WidgetDto> getWidgetDtosByCurrentUser();

  WidgetDto getWidgetDto(UUID widgetId);

  Widget getWidget(UUID widgetId);

  List<Widget> getWidgetsByTenant(Tenant tenant);

  void saveWidget(UUID widgetId, WidgetDto widgetDto);
}
