package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.ApplicationDto;
import com.startupmvp.api.dto.OrganizationDto;
import com.startupmvp.api.dto.PlatformDto;
import com.startupmvp.api.model.Application;
import com.startupmvp.api.model.Organization;
import com.startupmvp.api.model.Platform;

import java.util.UUID;

/**
 * Mapper class for converting between Application entity and ApplicationDto.
 */
public class ApplicationMapper {

    /**
     * Converts an Application entity to an ApplicationDto.
     *
     * @param application the Application entity to convert
     * @return the corresponding ApplicationDto
     */
    public static ApplicationDto toDto(Application application) {
        if (application == null) {
            return null;
        }

        ApplicationDto dto = new ApplicationDto();
        dto.setApplicationId(application.getApplicationId() != null ? application.getApplicationId().toString() : null);
        dto.setApplicationName(application.getApplicationName());
        dto.setDescription(application.getDescription());
        
        // Map organization
        if (application.getOrganization() != null) {
            dto.setOrganizationDto(OrganizationMapper.toDto(application.getOrganization()));
        }
        
        // Map platform
        if (application.getPlatformCode() != null) {
            // Since we only have the platform code in the Application entity,
            // we create a minimal PlatformDto with just the code
            PlatformDto platformDto = new PlatformDto();
            platformDto.setPlatformCode(application.getPlatformCode());
            dto.setPlatformDto(platformDto);
        }
        
        return dto;
    }

    /**
     * Converts an ApplicationDto to an Application entity.
     *
     * @param dto the ApplicationDto to convert
     * @return the corresponding Application entity
     */
    public static Application toEntity(ApplicationDto dto) {
        if (dto == null) {
            return null;
        }

        Application application = new Application();
        
        // Convert String ID to UUID if present
        if (dto.getApplicationId() != null && !dto.getApplicationId().isEmpty()) {
            try {
                application.setApplicationId(UUID.fromString(dto.getApplicationId()));
            } catch (IllegalArgumentException e) {
                // Handle invalid UUID format
                // In a real application, you might want to log this or throw a custom exception
            }
        }
        
        application.setApplicationName(dto.getApplicationName());
        application.setDescription(dto.getDescription());
        
        // Map organization
        if (dto.getOrganizationDto() != null) {
            application.setOrganization(OrganizationMapper.toEntity(dto.getOrganizationDto()));
        }
        
        // Map platform
        if (dto.getPlatformDto() != null) {
            application.setPlatformCode(dto.getPlatformDto().getPlatformCode());
        }
        
        return application;
    }
}