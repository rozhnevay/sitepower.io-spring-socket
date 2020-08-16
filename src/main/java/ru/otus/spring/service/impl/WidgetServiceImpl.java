package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.WidgetDto;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.Widget;
import ru.otus.spring.repository.WidgetRepository;
import ru.otus.spring.service.UserService;
import ru.otus.spring.service.WidgetService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WidgetServiceImpl implements WidgetService {

  public static final String WIDGET_NOT_FOUND = "Widget not found";

  private final WidgetRepository widgetRepository;

  private final ModelMapper mapper;

  private final UserService userService;

  @Override
  public Widget getWidget(UUID widgetId) {
    return widgetRepository.findById(widgetId).orElseThrow(() -> new RuntimeException(WIDGET_NOT_FOUND));
  }

  @Override
  public List<Widget> getWidgetsByTenant(Tenant tenant) {
    return widgetRepository.findAllByTenant(tenant);
  }

  @Override
  public void saveWidget(UUID widgetId, WidgetDto widgetDto) {
    Widget widget = getWidget(widgetId);
    widget.setColor(widgetDto.getColor());
    widget.setGradient(widgetDto.getGradient());
    widget.setLabel(widgetDto.getLabel());
    widget.setMessagePlaceholder(widgetDto.getMessagePlaceholder());
    widget.setPosition(widgetDto.getPosition());
    widgetRepository.save(widget);
  }

  @Override
  public List<WidgetDto> getWidgetDtosByCurrentUser() {
    return widgetRepository.findAllByTenant(userService.getCurrentUser().getTenant())
        .stream().map(widget -> mapper.map(widget, WidgetDto.class)).collect(Collectors.toList());
  }

  @Override
  public WidgetDto getWidgetDto(UUID widgetId) {
    Widget widget = getWidget(widgetId);
    return mapper.map(widget, WidgetDto.class);
  }
}
