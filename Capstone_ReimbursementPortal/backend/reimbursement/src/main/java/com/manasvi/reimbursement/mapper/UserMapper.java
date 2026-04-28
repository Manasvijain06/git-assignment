package com.manasvi.reimbursement.mapper;

import com.manasvi.reimbursement.dto.Request.UserRequest;
import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.exception.ValidationException;

public class UserMapper {
    // DTO → Entity
    public static User toEntity(UserRequest dto) {

        if (dto.getRole() == null) {
            throw new ValidationException("Role is required");
        }

        User user = new User();

        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        return user;
    }

    // Entity → Response DTO
    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        return response;
    }
}
