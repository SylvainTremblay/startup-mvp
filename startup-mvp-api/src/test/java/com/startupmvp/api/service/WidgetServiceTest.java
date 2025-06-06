package com.startupmvp.api.service;

import com.startupmvp.api.model.Widget;
import com.startupmvp.api.repository.WidgetRepository;
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
class WidgetServiceTest {
    @Mock
    private WidgetRepository widgetRepository;
    @InjectMocks
    private WidgetService widgetService;

    @Test
    void findAll_returnsList() {
        when(widgetRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(widgetService.findAll()).isEmpty();
    }

    @Test
    void findById_returnsOptional() {
        Long id = 1L;
        when(widgetRepository.findById(id)).thenReturn(Optional.empty());
        assertThat(widgetService.findById(id)).isEmpty();
    }

    @Test
    void save_delegatesToRepository() {
        Widget widget = new Widget();
        when(widgetRepository.save(widget)).thenReturn(widget);
        assertThat(widgetService.save(widget)).isEqualTo(widget);
    }

    @Test
    void deleteById_delegatesToRepository() {
        Long id = 1L;
        widgetService.deleteById(id);
        verify(widgetRepository).deleteById(id);
    }
}

