package com.startupmvp.api.service;

import com.startupmvp.api.model.User;
import com.startupmvp.api.repository.UserRepository;
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
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void findAll_returnsList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(userService.findAll()).isEmpty();
    }

    @Test
    void findById_returnsOptional() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThat(userService.findById(id)).isEmpty();
    }

    @Test
    void save_delegatesToRepository() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.save(user)).isEqualTo(user);
    }

    @Test
    void deleteById_delegatesToRepository() {
        Long id = 1L;
        userService.deleteById(id);
        verify(userRepository).deleteById(id);
    }
}

