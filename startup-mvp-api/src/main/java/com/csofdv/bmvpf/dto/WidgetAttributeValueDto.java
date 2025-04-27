package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WidgetAttributeValueDto {

    private long widgetAttributeValueId;

    private AttributeTypeDto attributeType;

    private WidgetTypeDto widgetType;

    private String attributeValue;
}
