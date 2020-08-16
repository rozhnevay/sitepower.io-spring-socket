package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.WidgetDto;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.Widget;
import ru.otus.spring.repository.WidgetRepository;
import ru.otus.spring.service.WidgetService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WidgetServiceImpl implements WidgetService {

  public static final String WIDGET_NOT_FOUND = "Widget not found";

  private final WidgetRepository widgetRepository;

  private final ModelMapper mapper;

  @Override
  public Widget getWidget(UUID widgetId) {
    return widgetRepository.findById(widgetId).orElseThrow(() -> new RuntimeException(WIDGET_NOT_FOUND));
  }

  @Override
  public List<Widget> getWidgetsByTenant(Tenant tenant) {
    return widgetRepository.findAllByTenant(tenant);
  }

  @Override
  public WidgetDto getWidgetDto(UUID widgetId) {
    Widget widget = getWidget(widgetId);
    return mapper.map(widget, WidgetDto.class);
  }
}
