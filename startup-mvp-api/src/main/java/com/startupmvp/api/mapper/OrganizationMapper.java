package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.OrganizationDto;
import com.startupmvp.api.dto.OrganizationAddressDto;
import com.startupmvp.api.model.Organization;
import com.startupmvp.api.model.OrganizationAddress;

import java.util.UUID;

/**
 * Mapper class for converting between Organization entity and OrganizationDto.
 */
public class OrganizationMapper {

    /**
     * Converts an Organization entity to an OrganizationDto.
     *
     * @param organization the Organization entity to convert
     * @return the corresponding OrganizationDto
     */
    public static OrganizationDto toDto(Organization organization) {
        if (organization == null) {
            return null;
        }

        OrganizationDto dto = new OrganizationDto();
        dto.setOrganizationId(organization.getOrganizationId() != null ? organization.getOrganizationId().toString() : null);
        dto.setOrganizationName(organization.getOrganizationName());
        dto.setWebsite(organization.getWebsite());
        
        // Map organization address
        if (organization.getOrganizationAddress() != null) {
            dto.setOrganizationAddressDto(toAddressDto(organization.getOrganizationAddress()));
        }
        
        // Map billing address
        if (organization.getBillingAddress() != null) {
            dto.setBillingAddressDto(toAddressDto(organization.getBillingAddress()));
        }
        
        return dto;
    }

    /**
     * Converts an OrganizationDto to an Organization entity.
     *
     * @param dto the OrganizationDto to convert
     * @return the corresponding Organization entity
     */
    public static Organization toEntity(OrganizationDto dto) {
        if (dto == null) {
            return null;
        }

        Organization organization = new Organization();
        
        // Convert String ID to UUID if present
        if (dto.getOrganizationId() != null && !dto.getOrganizationId().isEmpty()) {
            try {
                organization.setOrganizationId(UUID.fromString(dto.getOrganizationId()));
            } catch (IllegalArgumentException e) {
                // Handle invalid UUID format
                // In a real application, you might want to log this or throw a custom exception
            }
        }
        
        organization.setOrganizationName(dto.getOrganizationName());
        organization.setWebsite(dto.getWebsite());
        
        // Map organization address
        if (dto.getOrganizationAddressDto() != null) {
            organization.setOrganizationAddress(toAddressEntity(dto.getOrganizationAddressDto()));
        }
        
        // Map billing address
        if (dto.getBillingAddressDto() != null) {
            organization.setBillingAddress(toAddressEntity(dto.getBillingAddressDto()));
        }
        
        return organization;
    }

    /**
     * Converts an OrganizationAddress entity to an OrganizationAddressDto.
     *
     * @param address the OrganizationAddress entity to convert
     * @return the corresponding OrganizationAddressDto
     */
    private static OrganizationAddressDto toAddressDto(OrganizationAddress address) {
        if (address == null) {
            return null;
        }

        OrganizationAddressDto dto = new OrganizationAddressDto();
        dto.setOrganizationAddressId(address.getOrganizationAddressId());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountryCode()); // Map countryCode to country
        dto.setPostalCode(address.getPostalCode());
        
        return dto;
    }

    /**
     * Converts an OrganizationAddressDto to an OrganizationAddress entity.
     *
     * @param dto the OrganizationAddressDto to convert
     * @return the corresponding OrganizationAddress entity
     */
    private static OrganizationAddress toAddressEntity(OrganizationAddressDto dto) {
        if (dto == null) {
            return null;
        }

        OrganizationAddress address = new OrganizationAddress();
        address.setOrganizationAddressId(dto.getOrganizationAddressId());
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountryCode(dto.getCountry()); // Map country to countryCode
        address.setPostalCode(dto.getPostalCode());
        
        return address;
    }
}