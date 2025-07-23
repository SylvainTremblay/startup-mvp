package com.startupmvp.api.controller;

import com.startupmvp.api.dto.ViewDto;
import com.startupmvp.api.mapper.ViewMapper;
import com.startupmvp.api.model.View;
import com.startupmvp.api.service.ViewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "View", description = "Operations related to views")
@RestController
@RequestMapping("/api/view")
public class ViewController {

    @Autowired
    private ViewService viewService;

    @GetMapping
    public List<ViewDto> getAllViews() {
        return viewService.findAll().stream()
                .map(ViewMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewDto> getViewById(@PathVariable Long id) {
        Optional<View> view = viewService.findById(id);
        return view.map(v -> ResponseEntity.ok(ViewMapper.toDto(v)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ViewDto createView(@RequestBody ViewDto viewDto) {
        View view = ViewMapper.toEntity(viewDto);
        View savedView = viewService.save(view);
        return ViewMapper.toDto(savedView);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViewDto> updateView(@PathVariable Long id, @RequestBody ViewDto viewDto) {
        Optional<View> existingViewOpt = viewService.findById(id);
        if (existingViewOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        View existingView = existingViewOpt.get();
        ViewMapper.updateEntity(existingView, viewDto);
        View updatedView = viewService.save(existingView);
        return ResponseEntity.ok(ViewMapper.toDto(updatedView));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteView(@PathVariable Long id) {
        if (viewService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        viewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

