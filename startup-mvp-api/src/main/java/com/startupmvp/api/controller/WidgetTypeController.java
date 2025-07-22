package com.startupmvp.api.controller;

import com.startupmvp.api.model.WidgetType;
import com.startupmvp.api.service.WidgetTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "WidgetType", description = "Operations related to widget types")
@RestController
@RequestMapping("/api/widgetType")
public class WidgetTypeController {

    @Autowired
    private WidgetTypeService widgetTypeService;

    @GetMapping
    public List<WidgetType> getAllWidgetTypes() {
        return widgetTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WidgetType> getWidgetTypeById(@PathVariable Long id) {
        Optional<WidgetType> WidgetType = widgetTypeService.findById(id);
        return WidgetType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<WidgetType> getWidgetTypeByName(@PathVariable String name) {
        Optional<WidgetType> WidgetType = widgetTypeService.findByName(name);
        return WidgetType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

