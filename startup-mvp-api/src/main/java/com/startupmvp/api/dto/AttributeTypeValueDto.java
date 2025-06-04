package com.startupmvp.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttributeTypeValueDto {


    private String attributeValue;

    private AttributeTypeDto AttributeTypeDto;
}
