package com.simplyfly.service;

import com.simplyfly.dto.request.UpdateUserRequest;
import com.simplyfly.dto.response.UserResponse;
import com.simplyfly.enums.Role;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.UserMapper;
import com.simplyfly.model.User;
import com.simplyfly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.simplyfly.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse getUserById(Long id){
        User user=userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.mapToResponse(user);
    }

    public PageResponse<UserResponse> getAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(
                page, size,
                Sort.by("fullName").ascending());

        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> content = userPage.getContent()
                .stream()
                .map(userMapper::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }

    public UserResponse updateUser(Long id,UpdateUserRequest request){
        User user=userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(request.fullName());
        user.setPhone(request.phone());

        return userMapper.mapToResponse(userRepository.save(user));
    }

    public void deleteUser(Long id){
        User user=userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setIsActive(false);
        userRepository.save(user);
    }

    public List<UserResponse> getUsersByRole(Role role){
        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::mapToResponse)
                .toList();
    }

    public List<UserResponse> searchUsers(String keyword){
        return userRepository.searchByNameOrEmail(keyword)
                .stream()
                .map(userMapper::mapToResponse)
                .toList();
    }

    public PageResponse<UserResponse> getActiveUsers(
            int page, int size) {

        Pageable pageable = PageRequest.of(
                page, size,
                Sort.by("fullName").ascending());

        Page<User> userPage = userRepository
                .findByIsActiveTrue(pageable);

        List<UserResponse> content = userPage.getContent()
                .stream()
                .map(userMapper::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }
}