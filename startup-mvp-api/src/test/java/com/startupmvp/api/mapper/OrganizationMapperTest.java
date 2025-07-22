package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.OrganizationDto;
import com.startupmvp.api.dto.OrganizationAddressDto;
import com.startupmvp.api.model.Organization;
import com.startupmvp.api.model.OrganizationAddress;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationMapperTest {

    @Test
    void testToDto() {
        // Create test data
        UUID organizationId = UUID.randomUUID();
        Organization organization = new Organization();
        organization.setOrganizationId(organizationId);
        organization.setOrganizationName("Test Organization");
        organization.setWebsite("https://testorg.com");

        // Create and set organization address
        OrganizationAddress orgAddress = new OrganizationAddress();
        orgAddress.setOrganizationAddressId(1L);
        orgAddress.setAddressLine1("123 Main St");
        orgAddress.setAddressLine2("Suite 100");
        orgAddress.setCity("Test City");
        orgAddress.setState("Test State");
        orgAddress.setCountryCode("US");
        orgAddress.setPostalCode("12345");
        organization.setOrganizationAddress(orgAddress);

        // Create and set billing address
        OrganizationAddress billingAddress = new OrganizationAddress();
        billingAddress.setOrganizationAddressId(2L);
        billingAddress.setAddressLine1("456 Billing St");
        billingAddress.setAddressLine2("Floor 2");
        billingAddress.setCity("Billing City");
        billingAddress.setState("Billing State");
        billingAddress.setCountryCode("CA");
        billingAddress.setPostalCode("67890");
        organization.setBillingAddress(billingAddress);

        // Convert to DTO
        OrganizationDto dto = OrganizationMapper.toDto(organization);

        // Verify conversion
        assertNotNull(dto);
        assertEquals(organizationId.toString(), dto.getOrganizationId());
        assertEquals("Test Organization", dto.getOrganizationName());
        assertEquals("https://testorg.com", dto.getWebsite());

        // Verify organization address
        assertNotNull(dto.getOrganizationAddressDto());
        assertEquals(1L, dto.getOrganizationAddressDto().getOrganizationAddressId());
        assertEquals("123 Main St", dto.getOrganizationAddressDto().getAddressLine1());
        assertEquals("Suite 100", dto.getOrganizationAddressDto().getAddressLine2());
        assertEquals("Test City", dto.getOrganizationAddressDto().getCity());
        assertEquals("Test State", dto.getOrganizationAddressDto().getState());
        assertEquals("US", dto.getOrganizationAddressDto().getCountry());
        assertEquals("12345", dto.getOrganizationAddressDto().getPostalCode());

        // Verify billing address
        assertNotNull(dto.getBillingAddressDto());
        assertEquals(2L, dto.getBillingAddressDto().getOrganizationAddressId());
        assertEquals("456 Billing St", dto.getBillingAddressDto().getAddressLine1());
        assertEquals("Floor 2", dto.getBillingAddressDto().getAddressLine2());
        assertEquals("Billing City", dto.getBillingAddressDto().getCity());
        assertEquals("Billing State", dto.getBillingAddressDto().getState());
        assertEquals("CA", dto.getBillingAddressDto().getCountry());
        assertEquals("67890", dto.getBillingAddressDto().getPostalCode());
    }

    @Test
    void testToEntity() {
        // Create test data
        String organizationId = UUID.randomUUID().toString();
        OrganizationDto dto = new OrganizationDto();
        dto.setOrganizationId(organizationId);
        dto.setOrganizationName("Test Organization");
        dto.setWebsite("https://testorg.com");

        // Create and set organization address
        OrganizationAddressDto orgAddressDto = new OrganizationAddressDto();
        orgAddressDto.setOrganizationAddressId(1L);
        orgAddressDto.setAddressLine1("123 Main St");
        orgAddressDto.setAddressLine2("Suite 100");
        orgAddressDto.setCity("Test City");
        orgAddressDto.setState("Test State");
        orgAddressDto.setCountry("US");
        orgAddressDto.setPostalCode("12345");
        dto.setOrganizationAddressDto(orgAddressDto);

        // Create and set billing address
        OrganizationAddressDto billingAddressDto = new OrganizationAddressDto();
        billingAddressDto.setOrganizationAddressId(2L);
        billingAddressDto.setAddressLine1("456 Billing St");
        billingAddressDto.setAddressLine2("Floor 2");
        billingAddressDto.setCity("Billing City");
        billingAddressDto.setState("Billing State");
        billingAddressDto.setCountry("CA");
        billingAddressDto.setPostalCode("67890");
        dto.setBillingAddressDto(billingAddressDto);

        // Convert to entity
        Organization organization = OrganizationMapper.toEntity(dto);

        // Verify conversion
        assertNotNull(organization);
        assertEquals(UUID.fromString(organizationId), organization.getOrganizationId());
        assertEquals("Test Organization", organization.getOrganizationName());
        assertEquals("https://testorg.com", organization.getWebsite());

        // Verify organization address
        assertNotNull(organization.getOrganizationAddress());
        assertEquals(1L, organization.getOrganizationAddress().getOrganizationAddressId());
        assertEquals("123 Main St", organization.getOrganizationAddress().getAddressLine1());
        assertEquals("Suite 100", organization.getOrganizationAddress().getAddressLine2());
        assertEquals("Test City", organization.getOrganizationAddress().getCity());
        assertEquals("Test State", organization.getOrganizationAddress().getState());
        assertEquals("US", organization.getOrganizationAddress().getCountryCode());
        assertEquals("12345", organization.getOrganizationAddress().getPostalCode());

        // Verify billing address
        assertNotNull(organization.getBillingAddress());
        assertEquals(2L, organization.getBillingAddress().getOrganizationAddressId());
        assertEquals("456 Billing St", organization.getBillingAddress().getAddressLine1());
        assertEquals("Floor 2", organization.getBillingAddress().getAddressLine2());
        assertEquals("Billing City", organization.getBillingAddress().getCity());
        assertEquals("Billing State", organization.getBillingAddress().getState());
        assertEquals("CA", organization.getBillingAddress().getCountryCode());
        assertEquals("67890", organization.getBillingAddress().getPostalCode());
    }

    @Test
    void testNullHandling() {
        // Test null entity to DTO
        assertNull(OrganizationMapper.toDto(null));

        // Test null DTO to entity
        assertNull(OrganizationMapper.toEntity(null));

        // Test entity with null fields
        Organization organization = new Organization();
        organization.setOrganizationId(null);
        organization.setOrganizationName("Test Organization");
        organization.setOrganizationAddress(null);
        organization.setBillingAddress(null);

        OrganizationDto dto = OrganizationMapper.toDto(organization);
        assertNotNull(dto);
        assertNull(dto.getOrganizationId());
        assertNull(dto.getOrganizationAddressDto());
        assertNull(dto.getBillingAddressDto());

        // Test DTO with null fields
        dto = new OrganizationDto();
        dto.setOrganizationId(null);
        dto.setOrganizationName("Test Organization");
        dto.setOrganizationAddressDto(null);
        dto.setBillingAddressDto(null);

        organization = OrganizationMapper.toEntity(dto);
        assertNotNull(organization);
        assertNull(organization.getOrganizationId());
        assertNull(organization.getOrganizationAddress());
        assertNull(organization.getBillingAddress());
    }

    @Test
    void testInvalidUuidHandling() {
        // Test invalid UUID format
        OrganizationDto dto = new OrganizationDto();
        dto.setOrganizationId("invalid-uuid");
        
        Organization organization = OrganizationMapper.toEntity(dto);
        assertNotNull(organization);
        assertNull(organization.getOrganizationId());
    }
}