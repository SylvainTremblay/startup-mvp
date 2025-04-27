package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class WidgetTypeAttributeDto {

    private long widgetTypeAttributeId;

    private String name;

    private AttributeTypeDto attributeType;

    private LocalDateTime timestamp;

    private Boolean required = Boolean.FALSE;

    private Boolean attributeNameRequired = Boolean.FALSE;

    private WidgetTypeDto widgetType;
}
