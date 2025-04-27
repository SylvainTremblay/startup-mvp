package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ApplicationDto {

    private OrganizationDto organizationDto;

    private PlatformDto platformDto;

    private String applicationId;

    private String applicationName;

    private String description;

    private List<ViewDto> viewDtos = new ArrayList<>();

    public ViewDto getMainViewDto() {
        if (viewDtos == null || viewDtos.isEmpty()) {
            return null;
        }
        return viewDtos.stream()
                .filter(ViewDto::getMainView)
                .findFirst()
                .orElse(null);
    }
}
