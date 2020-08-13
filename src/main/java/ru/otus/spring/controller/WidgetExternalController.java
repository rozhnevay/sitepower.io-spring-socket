package ru.otus.spring.controller;

import java.util.ArrayList;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.dto.MessageDto;
import ru.otus.spring.dto.WidgetDto;

@RequestMapping(path = "/api-widget/widget")
public interface WidgetExternalController {

  @GetMapping("/{widgetId}")
  WidgetDto getWidget(@PathVariable("widgetId") UUID widgetId);

}
