package com.startupmvp.api.controller;

import com.startupmvp.api.dto.ApplicationDto;
import com.startupmvp.api.mapper.ApplicationMapper;
import com.startupmvp.api.model.Application;
import com.startupmvp.api.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public List<ApplicationDto> getAllApplications() {
        return applicationService.findAll().stream()
                .map(ApplicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable UUID id) {
        Optional<Application> application = applicationService.findById(id);
        return application.map(app -> ResponseEntity.ok(ApplicationMapper.toDto(app)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ApplicationDto createApplication(@RequestBody ApplicationDto applicationDto) {
        // Set application Id
        applicationDto.setApplicationId(UUID.randomUUID().toString());
        Application application = ApplicationMapper.toEntity(applicationDto);
        Application savedApplication = applicationService.save(application);
        return ApplicationMapper.toDto(savedApplication);
    }

    @PutMapping("/")
    public ResponseEntity<ApplicationDto> updateApplication(@RequestBody ApplicationDto applicationDto) {
        Application application = ApplicationMapper.toEntity(applicationDto);
        if (application.getApplicationId() == null || 
            applicationService.findById(application.getApplicationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Application updatedApplication = applicationService.save(application);
        return ResponseEntity.ok(ApplicationMapper.toDto(updatedApplication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID id) {
        if (applicationService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

