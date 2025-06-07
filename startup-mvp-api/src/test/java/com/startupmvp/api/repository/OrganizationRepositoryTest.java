package com.startupmvp.api.repository;

import com.startupmvp.api.model.Organization;
import com.startupmvp.api.model.OrganizationAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OrganizationRepositoryTest {
    @Autowired
    private OrganizationRepository repository;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
    }

    // Test insert organization. First, we need to create an organization entity.
    // Then we can use the repository to save it and use the find method to verify
    // that it was saved correctly.
    @Test
    void testInsertIFindDeleteOrganization() {
        // Create organization  entity
        Organization organization = new Organization();
        organization.setOrganizationId(UUID.randomUUID()); // Ensure ID is null for new entity
        organization.setOrganizationName("Test Organization");
        organization.setWebsite("https://test-organization.com");
        // Create OrganizationAddress entity
        OrganizationAddress organizationAddress = new OrganizationAddress();
        organizationAddress.setAddressLine1("123 Test St");
        organizationAddress.setCity("Test City");
        organizationAddress.setState("TS");
        organizationAddress.setPostalCode("12345");
        organizationAddress.setCountryCode("AA");
        organization.setOrganizationAddress(organizationAddress);
        // Save organization entity
        Organization savedOrganization = repository.save(organization);
        // Load the organization by ID
        Organization foundOrganization = repository.findById(savedOrganization.getOrganizationId()).orElse(null);
        // Verify that the organization was saved correctly using value from Organization entity
        assertThat(foundOrganization).isNotNull();
        assertThat(foundOrganization.getOrganizationName()).isEqualTo(organization.getOrganizationName());
        assertThat(foundOrganization.getWebsite()).isEqualTo(organization.getWebsite());
        // Verify that the organization address was saved correctly
        assertThat(foundOrganization.getOrganizationAddress()).isNotNull();
        assertThat(foundOrganization.getOrganizationAddress().getAddressLine1()).isEqualTo(organizationAddress.getAddressLine1());
        assertThat(foundOrganization.getOrganizationAddress().getCity()).isEqualTo(organizationAddress.getCity());
        assertThat(foundOrganization.getOrganizationAddress().getState()).isEqualTo(organizationAddress.getState());
        assertThat(foundOrganization.getOrganizationAddress().getPostalCode()).isEqualTo(organizationAddress.getPostalCode());
        assertThat(foundOrganization.getOrganizationAddress().getCountryCode()).isEqualTo(organizationAddress.getCountryCode());
        // Clean up by deleting the organization
        repository.deleteById(savedOrganization.getOrganizationId());
        // Verify that the organization was deleted
        assertThat(repository.findById(savedOrganization.getOrganizationId())).isEmpty();
    }
}


