package com.startupmvp.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ViewDto {

    private String name;

    private Boolean stateless = Boolean.TRUE;

    private Boolean mainView = Boolean.FALSE;

    private WidgetDto mainWidgetDto;

    private ViewModelDto viewModelDto;
}
