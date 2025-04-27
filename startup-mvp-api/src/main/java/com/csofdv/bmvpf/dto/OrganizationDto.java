package com.csofdv.bmvpf.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class OrganizationDto {

    private String organizationId;
    private String organizationName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String countryCode;
}
