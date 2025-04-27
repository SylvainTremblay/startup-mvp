package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AttributeTypeValueDto {

    private long attributeTypeValueId;

    private AttributeTypeDto attributeType;

    private WidgetTypeDto widgetType;

    private String value;

    private LocalDateTime timestamp;
}
