package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.PlatformDto;
import com.startupmvp.api.dto.WidgetTypeDto;
import com.startupmvp.api.model.Platform;
import com.startupmvp.api.model.WidgetType;

/**
 * Mapper class for converting between WidgetType entity and WidgetTypeDto.
 */
public class WidgetTypeMapper {

    /**
     * Converts a WidgetType entity to a WidgetTypeDto.
     *
     * @param widgetType the WidgetType entity to convert
     * @return the corresponding WidgetTypeDto
     */
    public static WidgetTypeDto toDto(WidgetType widgetType) {
        if (widgetType == null) {
            return null;
        }

        WidgetTypeDto dto = new WidgetTypeDto(widgetType.getName());
        dto.setWidgetTypeId(widgetType.getWidgetTypeId());
        dto.setSincePlatformVersion(widgetType.getSincePlatformVersion());
        dto.setUntilPlatformVersion(widgetType.getUntilPlatformVersion());
        
        // Map platform
        if (widgetType.getPlatform() != null) {
            PlatformDto platformDto = new PlatformDto();
            platformDto.setPlatformCode(widgetType.getPlatform().getPlatformCode());
            platformDto.setPlatformName(widgetType.getPlatform().getPlatformName());
            platformDto.setPlatformVersion(widgetType.getPlatform().getPlatformVersion());
            platformDto.setVersionDate(widgetType.getPlatform().getVersionDate());
            dto.setPlatform(platformDto);
        }
        
        // Note: description, actions, and attributeTypes in WidgetTypeDto 
        // don't have direct counterparts in WidgetType entity
        // They would need to be populated from other sources if needed
        
        return dto;
    }

    /**
     * Converts a WidgetTypeDto to a WidgetType entity.
     *
     * @param dto the WidgetTypeDto to convert
     * @return the corresponding WidgetType entity
     */
    public static WidgetType toEntity(WidgetTypeDto dto) {
        if (dto == null) {
            return null;
        }

        WidgetType widgetType = new WidgetType();
        widgetType.setWidgetTypeId(dto.getWidgetTypeId());
        widgetType.setName(dto.getName());
        widgetType.setSincePlatformVersion(dto.getSincePlatformVersion());
        widgetType.setUntilPlatformVersion(dto.getUntilPlatformVersion());
        
        // Map platform
        if (dto.getPlatform() != null) {
            Platform platform = new Platform();
            platform.setPlatformCode(dto.getPlatform().getPlatformCode());
            platform.setPlatformName(dto.getPlatform().getPlatformName());
            platform.setPlatformVersion(dto.getPlatform().getPlatformVersion());
            platform.setVersionDate(dto.getPlatform().getVersionDate());
            widgetType.setPlatform(platform);
        }
        
        // Note: description, actions, and attributeTypes in WidgetTypeDto 
        // are ignored as they don't have direct counterparts in WidgetType entity
        
        return widgetType;
    }
}