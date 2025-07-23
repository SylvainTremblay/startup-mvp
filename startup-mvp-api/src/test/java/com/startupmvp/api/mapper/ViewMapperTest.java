package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.ViewDto;
import com.startupmvp.api.model.View;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewMapperTest {

    @Test
    void testToDto() {
        // Create test data
        View view = new View();
        view.setViewId(1L);
        view.setName("Test View");
        view.setStateless(true);
        view.setMainView(false);
        view.setMainWidget(null);
        view.setApplication(null);

        // Convert to DTO
        ViewDto dto = ViewMapper.toDto(view);

        // Verify conversion
        assertNotNull(dto);
        assertEquals("Test View", dto.getName());
        assertEquals(true, dto.getStateless());
        assertEquals(false, dto.getMainView());
        assertNull(dto.getMainWidgetDto());
        assertNull(dto.getViewModelDto());
    }

    @Test
    void testToEntity() {
        // Create test data
        ViewDto dto = new ViewDto();
        dto.setName("Test View");
        dto.setStateless(true);
        dto.setMainView(false);
        dto.setMainWidgetDto(null);
        dto.setViewModelDto(null);

        // Convert to entity
        View view = ViewMapper.toEntity(dto);

        // Verify conversion
        assertNotNull(view);
        assertNull(view.getViewId()); // ID is not set by mapper
        assertEquals("Test View", view.getName());
        assertEquals(true, view.getStateless());
        assertEquals(false, view.getMainView());
        assertNull(view.getMainWidget());
        assertNull(view.getApplication()); // Application is not set by mapper
    }

    @Test
    void testUpdateEntity() {
        // Create existing entity
        View existingView = new View();
        existingView.setViewId(1L);
        existingView.setName("Original View");
        existingView.setStateless(false);
        existingView.setMainView(true);
        existingView.setMainWidget(null);
        existingView.setApplication(null);

        // Create DTO with updated data
        ViewDto dto = new ViewDto();
        dto.setName("Updated View");
        dto.setStateless(true);
        dto.setMainView(false);
        dto.setMainWidgetDto(null);
        dto.setViewModelDto(null);

        // Update entity with DTO data
        View updatedView = ViewMapper.updateEntity(existingView, dto);

        // Verify update
        assertNotNull(updatedView);
        assertEquals(1L, updatedView.getViewId()); // ID should remain unchanged
        assertEquals("Updated View", updatedView.getName());
        assertEquals(true, updatedView.getStateless());
        assertEquals(false, updatedView.getMainView());
        assertNull(updatedView.getMainWidget());
        assertNull(updatedView.getApplication()); // Application should remain unchanged
    }

    @Test
    void testNullHandling() {
        // Test null entity to DTO
        assertNull(ViewMapper.toDto(null));

        // Test null DTO to entity
        assertNull(ViewMapper.toEntity(null));

        // Test null DTO for update
        View view = new View();
        view.setName("Test View");
        assertSame(view, ViewMapper.updateEntity(view, null));

        // Test null entity for update
        assertNull(ViewMapper.updateEntity(null, new ViewDto()));
    }
}