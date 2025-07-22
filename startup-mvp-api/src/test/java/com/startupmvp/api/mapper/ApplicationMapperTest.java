package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.ApplicationDto;
import com.startupmvp.api.dto.OrganizationDto;
import com.startupmvp.api.dto.PlatformDto;
import com.startupmvp.api.model.Application;
import com.startupmvp.api.model.Organization;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationMapperTest {

    @Test
    void testToDto() {
        // Create test data
        UUID applicationId = UUID.randomUUID();
        Application application = new Application();
        application.setApplicationId(applicationId);
        application.setApplicationName("Test Application");
        application.setDescription("Test Description");
        application.setPlatformCode("WEB");

        // Create and set organization
        Organization organization = new Organization();
        UUID organizationId = UUID.randomUUID();
        organization.setOrganizationId(organizationId);
        organization.setOrganizationName("Test Organization");
        application.setOrganization(organization);

        // Convert to DTO
        ApplicationDto dto = ApplicationMapper.toDto(application);

        // Verify conversion
        assertNotNull(dto);
        assertEquals(applicationId.toString(), dto.getApplicationId());
        assertEquals("Test Application", dto.getApplicationName());
        assertEquals("Test Description", dto.getDescription());

        // Verify organization
        assertNotNull(dto.getOrganizationDto());
        assertEquals(organizationId.toString(), dto.getOrganizationDto().getOrganizationId());
        assertEquals("Test Organization", dto.getOrganizationDto().getOrganizationName());

        // Verify platform
        assertNotNull(dto.getPlatformDto());
        assertEquals("WEB", dto.getPlatformDto().getPlatformCode());
    }

    @Test
    void testToEntity() {
        // Create test data
        String applicationId = UUID.randomUUID().toString();
        ApplicationDto dto = new ApplicationDto();
        dto.setApplicationId(applicationId);
        dto.setApplicationName("Test Application");
        dto.setDescription("Test Description");

        // Create and set organization DTO
        OrganizationDto organizationDto = new OrganizationDto();
        String organizationId = UUID.randomUUID().toString();
        organizationDto.setOrganizationId(organizationId);
        organizationDto.setOrganizationName("Test Organization");
        dto.setOrganizationDto(organizationDto);

        // Create and set platform DTO
        PlatformDto platformDto = new PlatformDto();
        platformDto.setPlatformCode("WEB");
        dto.setPlatformDto(platformDto);

        // Convert to entity
        Application application = ApplicationMapper.toEntity(dto);

        // Verify conversion
        assertNotNull(application);
        assertEquals(UUID.fromString(applicationId), application.getApplicationId());
        assertEquals("Test Application", application.getApplicationName());
        assertEquals("Test Description", application.getDescription());
        assertEquals("WEB", application.getPlatformCode());

        // Verify organization
        assertNotNull(application.getOrganization());
        assertEquals(UUID.fromString(organizationId), application.getOrganization().getOrganizationId());
        assertEquals("Test Organization", application.getOrganization().getOrganizationName());
    }

    @Test
    void testNullHandling() {
        // Test null entity to DTO
        assertNull(ApplicationMapper.toDto(null));

        // Test null DTO to entity
        assertNull(ApplicationMapper.toEntity(null));

        // Test entity with null fields
        Application application = new Application();
        application.setApplicationId(null);
        application.setApplicationName("Test Application");
        application.setOrganization(null);
        application.setPlatformCode(null);

        ApplicationDto dto = ApplicationMapper.toDto(application);
        assertNotNull(dto);
        assertNull(dto.getApplicationId());
        assertNull(dto.getOrganizationDto());
        assertNull(dto.getPlatformDto());

        // Test DTO with null fields
        dto = new ApplicationDto();
        dto.setApplicationId(null);
        dto.setApplicationName("Test Application");
        dto.setOrganizationDto(null);
        dto.setPlatformDto(null);

        application = ApplicationMapper.toEntity(dto);
        assertNotNull(application);
        assertNull(application.getApplicationId());
        assertNull(application.getOrganization());
        assertNull(application.getPlatformCode());
    }

    @Test
    void testInvalidUuidHandling() {
        // Test invalid UUID format
        ApplicationDto dto = new ApplicationDto();
        dto.setApplicationId("invalid-uuid");
        
        Application application = ApplicationMapper.toEntity(dto);
        assertNotNull(application);
        assertNull(application.getApplicationId());
    }
}