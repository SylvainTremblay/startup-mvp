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
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WidgetTypeMapperTest {

    @Test
    void testToDto() {
        // Create test data
        WidgetType widgetType = new WidgetType();
        widgetType.setWidgetTypeId(1L);
        widgetType.setName("Button");
        widgetType.setSincePlatformVersion("1.0");
        widgetType.setUntilPlatformVersion("2.0");

        // Create and set platform
        Platform platform = new Platform();
        platform.setPlatformCode("WEB");
        platform.setPlatformName("Web Platform");
        platform.setPlatformVersion("1.0");
        platform.setVersionDate(LocalDate.of(2023, 1, 1));
        widgetType.setPlatform(platform);

        // Convert to DTO
        WidgetTypeDto dto = WidgetTypeMapper.toDto(widgetType);

        // Verify conversion
        assertNotNull(dto);
        assertEquals(1L, dto.getWidgetTypeId());
        assertEquals("Button", dto.getName());
        assertEquals("1.0", dto.getSincePlatformVersion());
        assertEquals("2.0", dto.getUntilPlatformVersion());

        // Verify platform
        assertNotNull(dto.getPlatform());
        assertEquals("WEB", dto.getPlatform().getPlatformCode());
        assertEquals("Web Platform", dto.getPlatform().getPlatformName());
        assertEquals("1.0", dto.getPlatform().getPlatformVersion());
        assertEquals(LocalDate.of(2023, 1, 1), dto.getPlatform().getVersionDate());
    }

    @Test
    void testToEntity() {
        // Create test data
        WidgetTypeDto dto = new WidgetTypeDto("Button");
        dto.setWidgetTypeId(1L);
        dto.setSincePlatformVersion("1.0");
        dto.setUntilPlatformVersion("2.0");

        // Create and set platform DTO
        PlatformDto platformDto = new PlatformDto();
        platformDto.setPlatformCode("WEB");
        platformDto.setPlatformName("Web Platform");
        platformDto.setPlatformVersion("1.0");
        platformDto.setVersionDate(LocalDate.of(2023, 1, 1));
        dto.setPlatform(platformDto);

        // Convert to entity
        WidgetType widgetType = WidgetTypeMapper.toEntity(dto);

        // Verify conversion
        assertNotNull(widgetType);
        assertEquals(1L, widgetType.getWidgetTypeId());
        assertEquals("Button", widgetType.getName());
        assertEquals("1.0", widgetType.getSincePlatformVersion());
        assertEquals("2.0", widgetType.getUntilPlatformVersion());

        // Verify platform
        assertNotNull(widgetType.getPlatform());
        assertEquals("WEB", widgetType.getPlatform().getPlatformCode());
        assertEquals("Web Platform", widgetType.getPlatform().getPlatformName());
        assertEquals("1.0", widgetType.getPlatform().getPlatformVersion());
        assertEquals(LocalDate.of(2023, 1, 1), widgetType.getPlatform().getVersionDate());
    }

    @Test
    void testNullHandling() {
        // Test null entity to DTO
        assertNull(WidgetTypeMapper.toDto(null));

        // Test null DTO to entity
        assertNull(WidgetTypeMapper.toEntity(null));

        // Test entity with null fields
        WidgetType widgetType = new WidgetType();
        widgetType.setWidgetTypeId(1L);
        widgetType.setName("Button");
        widgetType.setPlatform(null);

        WidgetTypeDto dto = WidgetTypeMapper.toDto(widgetType);
        assertNotNull(dto);
        assertEquals("Button", dto.getName());
        assertNull(dto.getPlatform());

        // Test DTO with null fields
        dto = new WidgetTypeDto("Button");
        dto.setWidgetTypeId(1L);
        dto.setPlatform(null);

        widgetType = WidgetTypeMapper.toEntity(dto);
        assertNotNull(widgetType);
        assertEquals("Button", widgetType.getName());
        assertNull(widgetType.getPlatform());
    }
    
    @Test
    void testToDtoWithAttributes() {
        // Create test data
        WidgetType widgetType = new WidgetType();
        widgetType.setWidgetTypeId(1L);
        widgetType.setName("Button");
        
        // Create platform
        Platform platform = new Platform();
        platform.setPlatformCode("WEB");
        platform.setPlatformName("Web Platform");
        widgetType.setPlatform(platform);
        
        // Create attribute type
        AttributeType attributeType = new AttributeType();
        attributeType.setAttributeTypeId(1L);
        attributeType.setName("String");
        
        // Create widget type attributes
        List<WidgetTypeAttribute> attributes = new ArrayList<>();
        
        WidgetTypeAttribute attribute1 = new WidgetTypeAttribute();
        attribute1.setWidgetTypeAttributeId(1L);
        attribute1.setAttributeName("text");
        attribute1.setAttributeType(attributeType);
        attribute1.setRequired(true);
        attribute1.setAttributeNameRequired(false);
        attribute1.setWidgetType(widgetType);
        attributes.add(attribute1);
        
        WidgetTypeAttribute attribute2 = new WidgetTypeAttribute();
        attribute2.setWidgetTypeAttributeId(2L);
        attribute2.setAttributeName("color");
        attribute2.setAttributeType(attributeType);
        attribute2.setRequired(false);
        attribute2.setAttributeNameRequired(true);
        attribute2.setWidgetType(widgetType);
        attributes.add(attribute2);
        
        // Convert to DTO with attributes
        WidgetTypeDto dto = WidgetTypeMapper.toDtoWithAttributes(widgetType, attributes);
        
        // Verify conversion
        assertNotNull(dto);
        assertEquals(1L, dto.getWidgetTypeId());
        assertEquals("Button", dto.getName());
        
        // Verify attributes
        assertEquals(2, dto.getAttributeTypes().size());
        
        WidgetTypeAttributeDto textAttributeDto = dto.getAttributeTypes().get("text");
        assertNotNull(textAttributeDto);
        assertEquals(1L, textAttributeDto.getWidgetTypeAttributeId());
        assertEquals("text", textAttributeDto.getAttributeName());
        assertTrue(textAttributeDto.getRequired());
        assertFalse(textAttributeDto.getAttributeNameRequired());
        
        WidgetTypeAttributeDto colorAttributeDto = dto.getAttributeTypes().get("color");
        assertNotNull(colorAttributeDto);
        assertEquals(2L, colorAttributeDto.getWidgetTypeAttributeId());
        assertEquals("color", colorAttributeDto.getAttributeName());
        assertFalse(colorAttributeDto.getRequired());
        assertTrue(colorAttributeDto.getAttributeNameRequired());
        
        // Verify attribute types
        assertNotNull(textAttributeDto.getAttributeType());
        assertEquals(1L, textAttributeDto.getAttributeType().getAttributeTypeId());
        assertEquals("String", textAttributeDto.getAttributeType().getName());
        
        assertNotNull(colorAttributeDto.getAttributeType());
        assertEquals(1L, colorAttributeDto.getAttributeType().getAttributeTypeId());
        assertEquals("String", colorAttributeDto.getAttributeType().getName());
    }
    
    @Test
    void testToAttributeDto() {
        // Create test data
        WidgetType widgetType = new WidgetType();
        widgetType.setWidgetTypeId(1L);
        widgetType.setName("Button");
        
        AttributeType attributeType = new AttributeType();
        attributeType.setAttributeTypeId(1L);
        attributeType.setName("String");
        
        WidgetTypeAttribute attribute = new WidgetTypeAttribute();
        attribute.setWidgetTypeAttributeId(1L);
        attribute.setAttributeName("text");
        attribute.setAttributeType(attributeType);
        attribute.setRequired(true);
        attribute.setAttributeNameRequired(false);
        attribute.setWidgetType(widgetType);
        
        // Convert to DTO
        WidgetTypeAttributeDto dto = WidgetTypeMapper.toAttributeDto(attribute);
        
        // Verify conversion
        assertNotNull(dto);
        assertEquals(1L, dto.getWidgetTypeAttributeId());
        assertEquals("text", dto.getAttributeName());
        assertTrue(dto.getRequired());
        assertFalse(dto.getAttributeNameRequired());
        
        // Verify attribute type
        assertNotNull(dto.getAttributeType());
        assertEquals(1L, dto.getAttributeType().getAttributeTypeId());
        assertEquals("String", dto.getAttributeType().getName());
        
        // Verify no circular reference
        assertNull(dto.getWidgetType());
    }
    
    @Test
    void testCreateActionDto() {
        // Create action DTO
        WidgetTypeActionDto dto = WidgetTypeMapper.createActionDto("onClick");
        
        // Verify creation
        assertNotNull(dto);
        assertEquals("onClick", dto.getName());
    }
}