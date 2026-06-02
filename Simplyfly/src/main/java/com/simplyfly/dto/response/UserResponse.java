package com.simplyfly.dto.response;

import com.simplyfly.enums.Role;

import java.time.LocalDateTime;

public record UserResponse (
     Long id,
     String fullName,
     String email,
     String phone,
     Role role,
     Boolean isActive,
     LocalDateTime createdAt
){
}
