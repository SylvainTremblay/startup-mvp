package com.startupmvp.api.mapper;

import com.startupmvp.api.dto.UserDto;
import com.startupmvp.api.model.User;

/**
 * Mapper class for converting between User entity and UserDto.
 */
public class UserMapper {

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDto
     */
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId() != null ? user.getUserId().toString() : null);
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        
        // Map organization using OrganizationMapper
        if (user.getOrganization() != null) {
            dto.setOrganizationDto(OrganizationMapper.toDto(user.getOrganization()));
        }
        
        return dto;
    }

    /**
     * Converts a UserDto to a User entity.
     *
     * @param dto the UserDto to convert
     * @return the corresponding User entity
     */
    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        
        // Convert String ID to Long if present
        if (dto.getUserId() != null && !dto.getUserId().isEmpty()) {
            try {
                user.setUserId(Long.parseLong(dto.getUserId()));
            } catch (NumberFormatException e) {
                // Handle invalid Long format
                // In a real application, you might want to log this or throw a custom exception
            }
        }
        
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        
        // Map organization using OrganizationMapper
        if (dto.getOrganizationDto() != null) {
            user.setOrganization(OrganizationMapper.toEntity(dto.getOrganizationDto()));
        }
        
        return user;
    }

    /**
     * Updates an existing User entity with data from a UserDto.
     * This method preserves fields that should not be updated from the DTO,
     * such as passwordHash.
     *
     * @param existingUser the existing User entity to update
     * @param dto the UserDto containing the new data
     * @return the updated User entity
     */
    public static User updateEntity(User existingUser, UserDto dto) {
        if (existingUser == null || dto == null) {
            return existingUser;
        }
        
        existingUser.setFirstName(dto.getFirstName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        
        // Map organization using OrganizationMapper
        if (dto.getOrganizationDto() != null) {
            existingUser.setOrganization(OrganizationMapper.toEntity(dto.getOrganizationDto()));
        }
        
        return existingUser;
    }
}