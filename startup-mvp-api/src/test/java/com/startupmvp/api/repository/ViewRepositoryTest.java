package com.startupmvp.api.repository;

import com.startupmvp.api.model.Application;
import com.startupmvp.api.model.Organization;
import com.startupmvp.api.model.View;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ViewRepositoryTest {
    @Autowired
    private ViewRepository repository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
    }

    // Create view entity, save it and find it by Id.
    // Validate each fields using the loaded entity.
    // Delete the entity and verify that it was deleted.
    @Test
    public void testInsertIFindDeleteView() {
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
        applicationRepository.save(application);
        // Create view entity
        View view = new View();
        view.setName("Test View");
        view.setStateless(Boolean.TRUE);
        view.setMainView(Boolean.FALSE);
        view.setApplication(application);
        // Save view entity
        View savedView = repository.save(view);

        // Load the view by ID
        View foundView = repository.findById(savedView.getViewId()).orElse(null);

        // Verify that the view was saved correctly
        assertThat(foundView).isNotNull();
        assertThat(foundView.getName()).isEqualTo(view.getName());
        assertThat(foundView.getStateless()).isEqualTo(view.getStateless());
        assertThat(foundView.getMainView()).isEqualTo(view.getMainView());
        assertThat(foundView.getApplication()).isNotNull();
        assertThat(foundView.getApplication().getApplicationId()).isEqualTo(application.getApplicationId());
        // Clean up by deleting the view
        repository.deleteById(savedView.getViewId());
    }
}

