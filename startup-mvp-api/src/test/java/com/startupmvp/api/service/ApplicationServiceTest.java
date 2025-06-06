package com.startupmvp.api.service;

import com.startupmvp.api.model.Application;
import com.startupmvp.api.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void findAll_returnsList() {
        when(applicationRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(applicationService.findAll()).isEmpty();
    }

    @Test
    void findById_returnsOptional() {
        UUID id = UUID.randomUUID();
        when(applicationRepository.findById(id)).thenReturn(Optional.empty());
        assertThat(applicationService.findById(id)).isEmpty();
    }

    @Test
    void save_delegatesToRepository() {
        Application app = new Application();
        when(applicationRepository.save(app)).thenReturn(app);
        assertThat(applicationService.save(app)).isEqualTo(app);
    }

    @Test
    void deleteById_delegatesToRepository() {
        UUID id = UUID.randomUUID();
        applicationService.deleteById(id);
        verify(applicationRepository).deleteById(id);
    }
}

