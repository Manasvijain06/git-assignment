package com.manasvi.reimbursement.mapper;

import com.manasvi.reimbursement.dto.Request.UserRequest;
import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.exception.ValidationException;

/**
 * Mapper class responsible for converting between User entity and DTOs.
 */
public class UserMapper {
    /**
     * Converts a UserRequest DTO to a User entity.
     *
     * @param dto the UserRequest DTO containing incoming user information.
     * @return the mapped User entity.
     * @throws ValidationException if the role is not provided in the request.
     */
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

    /**
     * Converts a User entity to a UserResponse DTO.
     *
     * @param user the User entity retrieved from the database.
     * @return the mapped UserResponse DTO to be sent in API responses.
     */
    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        if (user.getManager() != null) {
            response.setManagerId(user.getManager().getId());
            response.setManagerName(user.getManager().getName());

        }
        return response;
    }
}
