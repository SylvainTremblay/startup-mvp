package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class WidgetTypeDto {

    private long widgetTypeId;

    private String name;

    private String description;

    private PlatformDto platform;

    private Map<String, WidgetTypeActionDto> actions = new HashMap<>();

    private Map<String, WidgetTypeAttributeDto> attributeTypes = new HashMap<>();

    public WidgetTypeDto(String name) {
        this.name = name;
    }

    public void addAttributeType(WidgetTypeAttributeDto attributeType) {
        attributeTypes.put(attributeType.getName(), attributeType);
    }

    public void addAction(WidgetTypeActionDto action) {
        actions.put(action.getName(), action);
    }
}
