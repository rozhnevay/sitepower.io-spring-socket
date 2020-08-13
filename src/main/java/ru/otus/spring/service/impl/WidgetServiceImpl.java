package ru.otus.spring.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.WidgetDto;
import ru.otus.spring.model.Widget;
import ru.otus.spring.repository.WidgetRepository;
import ru.otus.spring.service.WidgetService;

@Service
@RequiredArgsConstructor
public class WidgetServiceImpl implements WidgetService {
  private final WidgetRepository widgetRepository;

  @Override
  public WidgetDto getWidget(UUID widgetId) {
    Widget widget = widgetRepository.findById(widgetId).orElseThrow(() -> new RuntimeException("Widget not found"));
    WidgetDto widgetDto = new WidgetDto();
    widgetDto.setColor(widget.getColor());
    widgetDto.setGradient(widget.getGradient());
    widgetDto.setLabel(widget.getLabel());
    widgetDto.setMessagePlaceholder(widget.getMessagePlaceholder());
    return widgetDto;
  }
}
