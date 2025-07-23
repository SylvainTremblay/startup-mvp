package com.startupmvp.api.controller;

import com.startupmvp.api.dto.WidgetTypeDto;
import com.startupmvp.api.mapper.WidgetTypeMapper;
import com.startupmvp.api.model.WidgetType;
import com.startupmvp.api.service.WidgetTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "WidgetType", description = "Operations related to widget types")
@RestController
@RequestMapping("/api/widgetType")
public class WidgetTypeController {

    @Autowired
    private WidgetTypeService widgetTypeService;

    @GetMapping
    public List<WidgetTypeDto> getAllWidgetTypes() {
        return widgetTypeService.findAll().stream()
                .map(WidgetTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WidgetTypeDto> getWidgetTypeById(@PathVariable Long id) {
        Optional<WidgetType> widgetType = widgetTypeService.findById(id);
        return widgetType.map(wt -> ResponseEntity.ok(WidgetTypeMapper.toDto(wt)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<WidgetTypeDto> getWidgetTypeByName(@PathVariable String name) {
        Optional<WidgetType> widgetType = widgetTypeService.findByName(name);
        return widgetType.map(wt -> ResponseEntity.ok(WidgetTypeMapper.toDto(wt)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

