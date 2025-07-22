package com.startupmvp.api.controller;

import com.startupmvp.api.dto.OrganizationDto;
import com.startupmvp.api.mapper.OrganizationMapper;
import com.startupmvp.api.model.Organization;
import com.startupmvp.api.service.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Organization", description = "Operations related to organizations")
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public List<OrganizationDto> getAllOrganizations() {
        return organizationService.findAll().stream()
                .map(OrganizationMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable UUID id) {
        Optional<Organization> organization = organizationService.findById(id);
        return organization.map(org -> ResponseEntity.ok(OrganizationMapper.toDto(org)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrganizationDto createOrganization(@RequestBody OrganizationDto organizationDto) {
        // First Generate UUID
        organizationDto.setOrganizationId(UUID.randomUUID().toString());
        Organization organization = OrganizationMapper.toEntity(organizationDto);
        Organization savedOrganization = organizationService.save(organization);
        return OrganizationMapper.toDto(savedOrganization);
    }

    @PutMapping
    public ResponseEntity<OrganizationDto> updateOrganization(@RequestBody OrganizationDto organizationDto) {
        Organization organization = OrganizationMapper.toEntity(organizationDto);
        if (organization.getOrganizationId() == null || 
            organizationService.findById(organization.getOrganizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Organization updatedOrganization = organizationService.save(organization);
        return ResponseEntity.ok(OrganizationMapper.toDto(updatedOrganization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable UUID id) {
        if (organizationService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        organizationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

