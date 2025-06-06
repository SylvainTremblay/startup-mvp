package com.startupmvp.api.service;

import com.startupmvp.api.model.View;
import com.startupmvp.api.repository.ViewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ViewServiceTest {
    @Mock
    private ViewRepository viewRepository;
    @InjectMocks
    private ViewService viewService;

    @Test
    void findAll_returnsList() {
        when(viewRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(viewService.findAll()).isEmpty();
    }

    @Test
    void findById_returnsOptional() {
        Long id = 1L;
        when(viewRepository.findById(id)).thenReturn(Optional.empty());
        assertThat(viewService.findById(id)).isEmpty();
    }

    @Test
    void save_delegatesToRepository() {
        View view = new View();
        when(viewRepository.save(view)).thenReturn(view);
        assertThat(viewService.save(view)).isEqualTo(view);
    }

    @Test
    void deleteById_delegatesToRepository() {
        Long id = 1L;
        viewService.deleteById(id);
        verify(viewRepository).deleteById(id);
    }
}

