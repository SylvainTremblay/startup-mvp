package com.startupmvp.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AttributeTypeDto {

    private long attributeTypeId;

    private String name;

    private boolean enumeration;

    private LocalDateTime timestamp;

    public AttributeTypeDto(String name) {
        this.name = name;
    }

    public AttributeTypeDto() {
    }
}
