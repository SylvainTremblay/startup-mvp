package com.startupmvp.api.controller;

import com.startupmvp.api.dto.WidgetDto;
import com.startupmvp.api.mapper.WidgetMapper;
import com.startupmvp.api.model.Widget;
import com.startupmvp.api.model.WidgetAttributeValue;
import com.startupmvp.api.service.WidgetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Widget", description = "Operations related to widgets")
@RestController
@RequestMapping("/api/widgets")
public class WidgetController {

    @Autowired
    private WidgetService widgetService;

    @GetMapping
    public List<WidgetDto> getAllWidgets() {
        return widgetService.findAll().stream()
                .map(WidgetMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WidgetDto> getWidgetById(@PathVariable Long id) {
        Optional<Widget> widget = widgetService.findById(id);
        return widget.map(w -> ResponseEntity.ok(WidgetMapper.toDto(w)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public WidgetDto createWidget(@RequestBody WidgetDto widgetDto) {
        // Convert DTO to entity
        Widget widget = WidgetMapper.toEntity(widgetDto);
        
        // Save the widget
        Widget savedWidget = widgetService.save(widget);
        
        // Create attribute values
        List<WidgetAttributeValue> attributeValues = WidgetMapper.createAttributeValues(savedWidget, widgetDto);
        
        // Set attribute values and save again if needed
        if (!attributeValues.isEmpty()) {
            savedWidget.setAttributeValues(attributeValues);
            savedWidget = widgetService.save(savedWidget);
        }
        
        return WidgetMapper.toDto(savedWidget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WidgetDto> updateWidget(@PathVariable Long id, @RequestBody WidgetDto widgetDto) {
        Optional<Widget> existingWidgetOpt = widgetService.findById(id);
        
        if (existingWidgetOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Widget existingWidget = existingWidgetOpt.get();
        Widget updatedWidget = WidgetMapper.updateEntity(existingWidget, widgetDto);
        
        // Save the updated widget
        Widget savedWidget = widgetService.save(updatedWidget);
        
        // Create new attribute values
        List<WidgetAttributeValue> attributeValues = WidgetMapper.createAttributeValues(savedWidget, widgetDto);
        
        // Set attribute values and save again if needed
        if (!attributeValues.isEmpty()) {
            // Clear existing attribute values and add new ones
            savedWidget.getAttributeValues().clear();
            savedWidget.getAttributeValues().addAll(attributeValues);
            savedWidget = widgetService.save(savedWidget);
        }
        
        return ResponseEntity.ok(WidgetMapper.toDto(savedWidget));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWidget(@PathVariable Long id) {
        if (widgetService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        widgetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}