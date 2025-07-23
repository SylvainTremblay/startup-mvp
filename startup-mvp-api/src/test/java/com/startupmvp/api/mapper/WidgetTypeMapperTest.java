package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.PlatformDto;
import com.startupmvp.api.dto.WidgetTypeDto;
import com.startupmvp.api.model.Platform;
import com.startupmvp.api.model.WidgetType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
}