package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class WidgetDto {

    private long widgetId;

    private WidgetTypeDto widgetType;

    private Boolean constant = Boolean.FALSE;

    private Map<String, Object> attributes = new HashMap<>();

    private Map<String, String> actions = new HashMap<>();

    public WidgetDto() {
    }

    public WidgetDto(WidgetTypeDto widgetType) {
        this.widgetType = widgetType;
    }

    public void setAttribute(String name, Object value) {
        WidgetTypeAttributeDto widgetTypeAttributeDto = widgetType.getAttributeTypes().get(name);
        if (widgetTypeAttributeDto == null) {
            throw new IllegalArgumentException("Attribute " + name + " is not valid for widget type " + widgetType.getName());
        }
        if ("Array".equals(widgetTypeAttributeDto.getAttributeType().getName()) && !(value instanceof WidgetDto[])) {
            throw new IllegalArgumentException("Attribute " + name + " must be an array");
        } else if ("Widget".equals(widgetTypeAttributeDto.getAttributeType().getName()) && !(value instanceof WidgetDto)) {
            throw new IllegalArgumentException("Attribute " + name + " must be a widget");
        } else if ("Class".equals(widgetTypeAttributeDto.getAttributeType().getName()) && !(value instanceof String)) {
            throw new IllegalArgumentException("Attribute " + name + " must be a class");
        } else if ("int".equals(widgetTypeAttributeDto.getAttributeType().getName()) && !(value instanceof Integer)) {
            throw new IllegalArgumentException("Attribute " + name + " must be an int");
        } else if ("String".equals(widgetTypeAttributeDto.getAttributeType().getName()) && !(value instanceof String)) {
            throw new IllegalArgumentException("Attribute " + name + " must be a string");
        } else if (widgetTypeAttributeDto.getAttributeType().isEnumeration() && !(value instanceof AttributeTypeValueDto)) {
            throw new IllegalArgumentException("Attribute " + name + " must be an attribute type value");
        }
        attributes.put(name, value);
    }

    public void setAction(String name, String value) {
        WidgetTypeActionDto widgetTypeActionDto = widgetType.getActions().get(name);
        if (widgetTypeActionDto == null) {
            throw new IllegalArgumentException("Action " + name + " is not valid for widget type " + widgetType.getName());
        }
        actions.put(name, value);
    }
}
