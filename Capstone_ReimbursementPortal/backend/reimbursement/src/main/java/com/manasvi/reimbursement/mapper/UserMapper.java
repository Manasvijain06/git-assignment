package com.manasvi.reimbursement.mapper;

import com.manasvi.reimbursement.dto.*;
import com.manasvi.reimbursement.entity.*;
import com.manasvi.reimbursement.exception.*;

public class UserMapper {
    // DTO → Entity
    public static User toEntity(UserRequest dto) {

        if (dto.getRole() == null) {
            throw new BadRequestException("Role is required");
        }

        User user = new User();

        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setManagerId(dto.getManagerId());

        try {
            user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role.");
        }

        return user;
    }


    // Entity → Response DTO
    public static UserResponse toResponse(User user) {

        UserResponse dto = new UserResponse();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());

        return dto;
    }
}


