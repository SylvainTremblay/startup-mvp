package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.AttributeTypeValueDto;
import com.startupmvp.api.dto.WidgetDto;
import com.startupmvp.api.dto.WidgetTypeDto;
import com.startupmvp.api.model.AttributeType;
import com.startupmvp.api.model.Widget;
import com.startupmvp.api.model.WidgetAttributeValue;
import com.startupmvp.api.model.WidgetType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between Widget entity and WidgetDto.
 */
public class WidgetMapper {

    /**
     * Converts a Widget entity to a WidgetDto.
     *
     * @param widget the Widget entity to convert
     * @return the corresponding WidgetDto
     */
    public static WidgetDto toDto(Widget widget) {
        if (widget == null) {
            return null;
        }

        // Map widget type using WidgetTypeMapper
        WidgetTypeDto widgetTypeDto = WidgetTypeMapper.toDto(widget.getWidgetType());
        WidgetDto dto = new WidgetDto(widgetTypeDto);
        
        dto.setWidgetId(widget.getWidgetId());
        dto.setConstant(widget.getConstant());
        
        // Map attribute values to attributes map
        if (widget.getAttributeValues() != null) {
            for (WidgetAttributeValue attributeValue : widget.getAttributeValues()) {
                String attributeName = attributeValue.getAttributeType().getName();
                String value = attributeValue.getAttributeValue();
                
                // Convert the string value to the appropriate type based on the attribute type
                Object convertedValue = convertStringToTypedValue(attributeValue.getAttributeType(), value);
                
                // Add to attributes map
                dto.getAttributes().put(attributeName, convertedValue);
            }
        }
        
        return dto;
    }

    /**
     * Converts a WidgetDto to a Widget entity.
     *
     * @param dto the WidgetDto to convert
     * @return the corresponding Widget entity
     */
    public static Widget toEntity(WidgetDto dto) {
        if (dto == null) {
            return null;
        }

        Widget widget = new Widget();
        
        // Don't set ID for new entities
        if (dto.getWidgetId() > 0) {
            widget.setWidgetId(dto.getWidgetId());
        }
        
        widget.setWidgetType(WidgetTypeMapper.toEntity(dto.getWidgetType()));
        widget.setConstant(dto.getConstant());
        widget.setTimestamp(LocalDateTime.now());
        
        // Attribute values will be set when the widget is saved
        // as they need the widget ID
        
        return widget;
    }

    /**
     * Updates an existing Widget entity with data from a WidgetDto.
     *
     * @param existingWidget the existing Widget entity to update
     * @param dto the WidgetDto containing the new data
     * @return the updated Widget entity
     */
    public static Widget updateEntity(Widget existingWidget, WidgetDto dto) {
        if (existingWidget == null || dto == null) {
            return existingWidget;
        }
        
        // Update widget type if provided
        if (dto.getWidgetType() != null) {
            WidgetType widgetType = WidgetTypeMapper.toEntity(dto.getWidgetType());
            existingWidget.setWidgetType(widgetType);
        }
        
        existingWidget.setConstant(dto.getConstant());
        existingWidget.setTimestamp(LocalDateTime.now());
        
        // Attribute values will need to be updated separately
        // as they require more complex logic
        
        return existingWidget;
    }
    
    /**
     * Creates a list of WidgetAttributeValue entities from a WidgetDto.
     * This method should be called after the Widget entity has been saved
     * to ensure it has an ID.
     *
     * @param widget the Widget entity
     * @param dto the WidgetDto containing the attribute values
     * @return a list of WidgetAttributeValue entities
     */
    public static List<WidgetAttributeValue> createAttributeValues(Widget widget, WidgetDto dto) {
        if (widget == null || dto == null || dto.getAttributes() == null) {
            return new ArrayList<>();
        }
        
        List<WidgetAttributeValue> attributeValues = new ArrayList<>();
        
        for (String attributeName : dto.getAttributes().keySet()) {
            Object value = dto.getAttributes().get(attributeName);
            
            // Find the attribute type in the widget type
            AttributeType attributeType = findAttributeType(widget.getWidgetType(), attributeName);
            
            if (attributeType != null) {
                WidgetAttributeValue attributeValue = new WidgetAttributeValue();
                attributeValue.setWidget(widget);
                attributeValue.setAttributeType(attributeType);
                attributeValue.setAttributeValue(convertTypedValueToString(value));
                
                attributeValues.add(attributeValue);
            }
        }
        
        return attributeValues;
    }
    
    /**
     * Finds an AttributeType in a WidgetType by name.
     *
     * @param widgetType the WidgetType to search in
     * @param attributeName the name of the attribute to find
     * @return the AttributeType, or null if not found
     */
    private static AttributeType findAttributeType(WidgetType widgetType, String attributeName) {
        // This is a simplified implementation
        // In a real application, you would need to access the attribute types from the widget type
        // This might require additional repository access or service calls
        
        // For now, we'll return null as a placeholder
        return null;
    }
    
    /**
     * Converts a string value to the appropriate type based on the attribute type.
     *
     * @param attributeType the AttributeType defining the type of the value
     * @param value the string value to convert
     * @return the converted value
     */
    private static Object convertStringToTypedValue(AttributeType attributeType, String value) {
        // This is a simplified implementation
        // In a real application, you would need to handle different types based on the attribute type
        
        String typeName = attributeType.getName();
        
        if ("int".equals(typeName)) {
            return Integer.parseInt(value);
        } else if ("String".equals(typeName)) {
            return value;
        } else if ("Boolean".equals(typeName)) {
            return Boolean.parseBoolean(value);
        } else if (attributeType.getEnumeration() != null && attributeType.getEnumeration()) {
            // For enumerations, you might need to create an AttributeTypeValueDto
            AttributeTypeValueDto valueDto = new AttributeTypeValueDto();
            valueDto.setAttributeValue(value);
            return valueDto;
        }
        
        // Default to returning the string value
        return value;
    }
    
    /**
     * Converts a typed value to a string.
     *
     * @param value the typed value to convert
     * @return the string representation of the value
     */
    private static String convertTypedValueToString(Object value) {
        if (value == null) {
            return "";
        }
        
        if (value instanceof AttributeTypeValueDto) {
            return ((AttributeTypeValueDto) value).getAttributeValue();
        }
        
        return value.toString();
    }
}