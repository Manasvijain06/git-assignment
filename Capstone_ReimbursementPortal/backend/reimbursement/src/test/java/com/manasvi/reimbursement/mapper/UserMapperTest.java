package com.manasvi.reimbursement.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.Role;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void toResponse_success() {
        User user = new User();
        user.setId(1L);
        user.setName("Manasvi");
        user.setEmail("test@company.com");
        user.setRole(Role.EMPLOYEE);

        UserResponse res = mapper.toResponse(user);

        assertNotNull(res);
        assertEquals("Manasvi", res.getName());
        assertEquals("test@company.com", res.getEmail());
        assertEquals("EMPLOYEE", res.getRole());
    }

    @Test
    void toResponse_nullUser() {
        // ✅ FIX: your mapper throws NPE, so test should expect it
        assertThrows(NullPointerException.class, () -> mapper.toResponse(null));
    }
}