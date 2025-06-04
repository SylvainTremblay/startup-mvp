package com.startupmvp.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class WidgetTypeActionDto {

    private long widgetTypeActionId;

    private String name;

    private String description;

    private LocalDateTime timestamp;

    private WidgetTypeDto widgetType;
}
