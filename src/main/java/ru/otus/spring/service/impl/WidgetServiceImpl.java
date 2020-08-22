package ru.otus.spring.service.impl;

import java.time.Instant;
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
  public static final String DEFAULT_COLOR = "sitepower";
  public static final int DEFAULT_POSITION = 0;
  public static final int DEFAULT_GRADIENT = 0;
  public static final String DEFAULT_LABEL = "Онлайн-чат";
  public static final String DEFAULT_PLACEHOLDER = "Напишите нам";

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
  public void updateWidget(UUID widgetId, WidgetDto widgetDto) {
    Widget widget = getWidget(widgetId);
    saveWidget(widget, widgetDto);
  }

  @Override
  public void addWidget(WidgetDto widgetDto) {
    Widget widget = new Widget();

    widget.setTenant(userService.getCurrentUser().getTenant());
    widget.setOrigin(widgetDto.getOrigin());
    widget.setColor(DEFAULT_COLOR);
    widget.setGradient(DEFAULT_GRADIENT);
    widget.setLabel(DEFAULT_LABEL);
    widget.setMessagePlaceholder(DEFAULT_PLACEHOLDER);
    widget.setPosition(DEFAULT_POSITION);
    widget.setCreatedDate(Instant.now());
    widget.setUpdatedDate(Instant.now());

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

  private Widget saveWidget(Widget widget, WidgetDto widgetDto) {
    widget.setColor(widgetDto.getColor());
    widget.setGradient(widgetDto.getGradient());
    widget.setLabel(widgetDto.getLabel());
    widget.setMessagePlaceholder(widgetDto.getMessagePlaceholder());
    widget.setPosition(widgetDto.getPosition());
    widget.setUpdatedDate(Instant.now());
    return widgetRepository.save(widget);
  }
}
