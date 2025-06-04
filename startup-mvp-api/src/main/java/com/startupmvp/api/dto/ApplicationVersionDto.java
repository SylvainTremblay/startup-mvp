package com.startupmvp.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ApplicationVersionDto {

    private List<ViewDto> viewDtos;
}
