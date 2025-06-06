package com.startupmvp.api.service;

import com.startupmvp.api.model.Organization;
import com.startupmvp.api.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {
    @Mock
    private OrganizationRepository organizationRepository;
    @InjectMocks
    private OrganizationService organizationService;

    @Test
    void findAll_returnsList() {
        when(organizationRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(organizationService.findAll()).isEmpty();
    }

    @Test
    void findById_returnsOptional() {
        UUID id = UUID.randomUUID();
        when(organizationRepository.findById(id)).thenReturn(Optional.empty());
        assertThat(organizationService.findById(id)).isEmpty();
    }

    @Test
    void save_delegatesToRepository() {
        Organization org = new Organization();
        when(organizationRepository.save(org)).thenReturn(org);
        assertThat(organizationService.save(org)).isEqualTo(org);
    }

    @Test
    void deleteById_delegatesToRepository() {
        UUID id = UUID.randomUUID();
        organizationService.deleteById(id);
        verify(organizationRepository).deleteById(id);
    }
}

