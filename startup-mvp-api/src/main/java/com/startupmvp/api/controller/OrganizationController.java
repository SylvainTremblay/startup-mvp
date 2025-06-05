package com.startupmvp.api.controller;

import com.startupmvp.api.model.Organization;
import com.startupmvp.api.service.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Organization", description = "Operations related to organizations")
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable UUID id) {
        Optional<Organization> organization = organizationService.findById(id);
        return organization.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.save(organization);
    }

    @PutMapping
    public ResponseEntity<Organization> updateOrganization(@RequestBody Organization organization) {
        if (organizationService.findById(organization.getOrganizationId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organizationService.save(organization));
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

