package com.startupmvp.api.controller;

import com.startupmvp.api.dto.UserDto;
import com.startupmvp.api.mapper.UserMapper;
import com.startupmvp.api.model.User;
import com.startupmvp.api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "User", description = "Operations related to users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(u -> ResponseEntity.ok(UserMapper.toDto(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        User savedUser = userService.save(user);
        return UserMapper.toDto(savedUser);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        if (userDto.getUserId() == null || userDto.getUserId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Long userId = Long.parseLong(userDto.getUserId());
            Optional<User> existingUserOpt = userService.findById(userId);
            
            if (existingUserOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            User existingUser = existingUserOpt.get();
            User updatedUser = UserMapper.updateEntity(existingUser, userDto);
            User savedUser = userService.save(updatedUser);
            
            return ResponseEntity.ok(UserMapper.toDto(savedUser));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

