package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.ViewDto;
import com.startupmvp.api.dto.WidgetDto;
import com.startupmvp.api.model.View;

/**
 * Mapper class for converting between View entity and ViewDto.
 */
public class ViewMapper {

    /**
     * Converts a View entity to a ViewDto.
     *
     * @param view the View entity to convert
     * @return the corresponding ViewDto
     */
    public static ViewDto toDto(View view) {
        if (view == null) {
            return null;
        }

        ViewDto dto = new ViewDto();
        dto.setName(view.getName());
        dto.setStateless(view.getStateless());
        dto.setMainView(view.getMainView());
        
        // For now, we're not handling the conversion of mainWidget to mainWidgetDto
        // This would require a WidgetMapper, which is outside the scope of this task
        dto.setMainWidgetDto(null);
        
        // ViewDto has a viewModelDto field, but View doesn't have a corresponding field
        // For now, we're setting it to null
        dto.setViewModelDto(null);
        
        return dto;
    }

    /**
     * Converts a ViewDto to a View entity.
     *
     * @param dto the ViewDto to convert
     * @return the corresponding View entity
     */
    public static View toEntity(ViewDto dto) {
        if (dto == null) {
            return null;
        }

        View view = new View();
        view.setName(dto.getName());
        view.setStateless(dto.getStateless());
        view.setMainView(dto.getMainView());
        
        // For now, we're not handling the conversion of mainWidgetDto to mainWidget
        // This would require a WidgetMapper, which is outside the scope of this task
        view.setMainWidget(null);
        
        // ViewDto has a viewModelDto field, but View doesn't have a corresponding field
        // So we ignore it here
        
        return view;
    }

    /**
     * Updates an existing View entity with data from a ViewDto.
     *
     * @param view the View entity to update
     * @param dto the ViewDto containing the new data
     * @return the updated View entity
     */
    public static View updateEntity(View view, ViewDto dto) {
        if (view == null || dto == null) {
            return view;
        }

        view.setName(dto.getName());
        view.setStateless(dto.getStateless());
        view.setMainView(dto.getMainView());
        
        // For now, we're not handling the update of mainWidget
        // This would require a WidgetMapper, which is outside the scope of this task
        
        return view;
    }
}