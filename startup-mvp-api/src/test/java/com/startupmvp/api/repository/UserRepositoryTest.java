package com.startupmvp.api.repository;

import com.startupmvp.api.model.Organization;
import com.startupmvp.api.model.OrganizationAddress;
import com.startupmvp.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
    }

    // Test insert organization. First, we need to create an organization entity.
    // Then we can use the repository to save it and use the find method to verify
    // that it was saved correctly.
    @Test
    void testInsertIFindDeleteUser() {
        // Create organization  entity
        Organization organization = new Organization();
        organization.setOrganizationId(UUID.randomUUID()); // Ensure ID is null for new entity
        organization.setOrganizationName("Test Organization");
        organization.setWebsite("https://test-organization.com");
        organizationRepository.save(organization);
        // Create user  entity
        User user = new User();
        user.setEmail("test@user.com");
        user.setFirstName("test first name");
        user.setLastName("test last name");
        user.setPhoneNumber("01-01-01-01-01");
        user.setOrganization(organization);
        // Save user entity
        User savedUser = repository.save(user);
        // Load the user by ID
        User foundUser = repository.findById(savedUser.getUserId()).orElse(null);
        // Verify that the user was saved correctly using value from Organization entity
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(foundUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(foundUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(foundUser.getOrganization()).isNotNull();
        assertThat(foundUser.getOrganization().getOrganizationId()).isEqualTo(organization.getOrganizationId());
        // Clean up by deleting the user
        repository.deleteById(savedUser.getUserId());
        // Verify that the user was deleted
        assertThat(repository.findById(savedUser.getUserId())).isEmpty();

    }
}

