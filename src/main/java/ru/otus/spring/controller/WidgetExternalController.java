package ru.otus.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.dto.WidgetDto;

import java.util.UUID;

@RequestMapping(path = "/api-widget/widget")
public interface WidgetExternalController {

  @GetMapping("/{widgetId}")
  WidgetDto getWidget(@PathVariable("widgetId") UUID widgetId);

}
