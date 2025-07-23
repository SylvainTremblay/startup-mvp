package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.AttributeTypeDto;
import com.startupmvp.api.dto.PlatformDto;
import com.startupmvp.api.dto.WidgetTypeActionDto;
import com.startupmvp.api.dto.WidgetTypeAttributeDto;
import com.startupmvp.api.dto.WidgetTypeDto;
import com.startupmvp.api.model.AttributeType;
import com.startupmvp.api.model.Platform;
import com.startupmvp.api.model.WidgetType;
import com.startupmvp.api.model.WidgetTypeAttribute;

import java.util.List;

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
        
        // Note: description in WidgetTypeDto doesn't have a direct counterpart in WidgetType entity
        // actions and attributeTypes are handled by separate methods
        
        return dto;
    }

    /**
     * Converts a WidgetType entity to a WidgetTypeDto and populates its attributes.
     *
     * @param widgetType the WidgetType entity to convert
     * @param attributes the list of WidgetTypeAttribute entities associated with the WidgetType
     * @return the corresponding WidgetTypeDto with populated attributes
     */
    public static WidgetTypeDto toDtoWithAttributes(WidgetType widgetType, List<WidgetTypeAttribute> attributes) {
        WidgetTypeDto dto = toDto(widgetType);
        if (dto == null) {
            return null;
        }
        
        if (attributes != null) {
            for (WidgetTypeAttribute attribute : attributes) {
                if (attribute.getWidgetType().getWidgetTypeId().equals(widgetType.getWidgetTypeId())) {
                    WidgetTypeAttributeDto attributeDto = toAttributeDto(attribute);
                    dto.addAttributeType(attributeDto);
                }
            }
        }
        
        return dto;
    }
    
    /**
     * Converts a WidgetTypeAttribute entity to a WidgetTypeAttributeDto.
     *
     * @param attribute the WidgetTypeAttribute entity to convert
     * @return the corresponding WidgetTypeAttributeDto
     */
    public static WidgetTypeAttributeDto toAttributeDto(WidgetTypeAttribute attribute) {
        if (attribute == null) {
            return null;
        }
        
        WidgetTypeAttributeDto dto = new WidgetTypeAttributeDto();
        dto.setWidgetTypeAttributeId(attribute.getWidgetTypeAttributeId());
        dto.setAttributeName(attribute.getAttributeName());
        dto.setSincePlatformVersion(attribute.getSincePlatformVersion());
        dto.setUntilPlatformVersion(attribute.getUntilPlatformVersion());
        dto.setRequired(attribute.getRequired());
        dto.setAttributeNameRequired(attribute.getAttributeNameRequired());
        
        // Map AttributeType
        if (attribute.getAttributeType() != null) {
            AttributeTypeDto attributeTypeDto = new AttributeTypeDto();
            AttributeType attributeType = attribute.getAttributeType();
            attributeTypeDto.setAttributeTypeId(attributeType.getAttributeTypeId());
            attributeTypeDto.setName(attributeType.getName());
            dto.setAttributeType(attributeTypeDto);
        }
        
        // Don't set widgetType to avoid circular reference
        
        return dto;
    }
    
    /**
     * Creates a WidgetTypeActionDto with the given name.
     *
     * @param name the name of the action
     * @return the corresponding WidgetTypeActionDto
     */
    public static WidgetTypeActionDto createActionDto(String name) {
        WidgetTypeActionDto dto = new WidgetTypeActionDto();
        dto.setName(name);
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