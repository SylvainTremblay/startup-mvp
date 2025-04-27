package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class ViewModelDto {

    private Map<String, AttributeTypeDto> attributeTypes = new HashMap<>();

    private Map<String, Object> attributeValues = new HashMap<>();

    public void setAttributeValue(String name, Object value) {
        if (!attributeTypes.containsKey(name)) {
            throw new IllegalArgumentException("Attribute type " + name + " does not exist.");
        }
        attributeValues.put(name, value);
    }
}
