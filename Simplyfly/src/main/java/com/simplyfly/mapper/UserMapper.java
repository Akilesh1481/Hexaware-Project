package com.simplyfly.mapper;

import com.simplyfly.dto.request.RegisterRequest;
import com.simplyfly.dto.response.UserResponse;
import com.simplyfly.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper{

    public User mapToEntity(RegisterRequest dto){
        User user=new User();
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setRole(dto.role());
        user.setIsActive(true);
        return user;
    }

    public UserResponse mapToResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getIsActive(),
                user.getCreatedAt()
        );
    }
}