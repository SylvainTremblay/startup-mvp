package com.startupmvp.api.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrganizationDto {

    private String organizationId;
    private String organizationName;
    private OrganizationAddressDto organizationAddressDto;
    private OrganizationAddressDto billingAddressDto;
    private String website;
}
