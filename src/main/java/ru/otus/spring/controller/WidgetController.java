package ru.otus.spring.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.WidgetDto;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/api/widget")
public interface WidgetController {

  @GetMapping()
  List<WidgetDto> getWidgets();

  @PostMapping("/{widgetId}")
  void saveWidgetSettings(@PathVariable("widgetId") UUID widgetId, @RequestBody WidgetDto widgetDto);

  @PostMapping()
  void addWidget(@RequestBody WidgetDto widgetDto);
}
