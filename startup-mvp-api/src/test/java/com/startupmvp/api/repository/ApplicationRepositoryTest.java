package com.startupmvp.api.repository;

import com.startupmvp.api.model.Application;
import com.startupmvp.api.model.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationRepositoryTest {
    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
    }

    // Create application entity, save it and find it by Id.
    // Validate each fields using the loaded entity.
    // Delete the entity and verify that it was deleted.
    @Test
    public void testInsertIFindDeleteApplication() {
        // Create organization  entity
        Organization organization = new Organization();
        organization.setOrganizationId(UUID.randomUUID()); // Ensure ID is null for new entity
        organization.setOrganizationName("Test Organization");
        organization.setWebsite("https://test-organization.com");
        organizationRepository.save(organization);
        // Create application entity
        Application application = new Application();
        application.setApplicationId(UUID.randomUUID()); // Ensure ID is null for new entity
        application.setApplicationName("Test Application");
        application.setDescription("This is a test application.");
        application.setPlatformCode("HTML5");
        application.setOrganization(organization);
        // Save application entity
        Application savedApplication = repository.save(application);

        // Load the application by ID
        Application foundApplication = repository.findById(savedApplication.getApplicationId()).orElse(null);

        // Verify that the application was saved correctly
        assertThat(foundApplication).isNotNull();
        assertThat(foundApplication.getApplicationName()).isEqualTo(application.getApplicationName());
        assertThat(foundApplication.getDescription()).isEqualTo(application.getDescription());
        assertThat(foundApplication.getPlatformCode()).isEqualTo(application.getPlatformCode());
        assertThat(foundApplication.getOrganization()).isNotNull();
        assertThat(foundApplication.getOrganization().getOrganizationId()).isEqualTo(organization.getOrganizationId());
        // Clean up by deleting the application
        repository.deleteById(savedApplication.getApplicationId());

        // Verify that the application was deleted
        assertThat(repository.findById(savedApplication.getApplicationId())).isEmpty();
    }
}

