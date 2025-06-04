package com.startupmvp.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PlatformDto {

    private String platformCode;

    private String platformVersion;

    private LocalDate versionDate;

    private String platformName;

    private String description;

    private LocalDateTime timestamp;
}
