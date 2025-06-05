package com.startupmvp.api.controller;

import com.startupmvp.api.model.View;
import com.startupmvp.api.service.ViewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "View", description = "Operations related to views")
@RestController
@RequestMapping("/api/view")
public class ViewController {

    @Autowired
    private ViewService viewService;

    @GetMapping
    public List<View> getAllViews() {
        return viewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<View> getViewById(@PathVariable Long id) {
        Optional<View> view = viewService.findById(id);
        return view.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public View createView(@RequestBody View view) {
        return viewService.save(view);
    }

    @PutMapping("/")
    public ResponseEntity<View> updateView(@RequestBody View view) {
        if (viewService.findById(view.getViewId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viewService.save(view));
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

