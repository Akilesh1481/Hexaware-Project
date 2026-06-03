package com.simplyfly.service;

import com.simplyfly.dto.request.UpdateUserRequest;
import com.simplyfly.dto.response.PageResponse;
import com.simplyfly.dto.response.UserResponse;
import com.simplyfly.enums.Role;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.mapper.UserMapper;
import com.simplyfly.model.User;
import com.simplyfly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse getUserById(Long id){
        User user=userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        checkSelfOrAdminOrOwner(user);

        return userMapper.mapToResponse(user);
    }

    public PageResponse<UserResponse> getAllUsers(int page,int size){
        checkAdminOrOwner();

        Pageable pageable=PageRequest.of(page,size,Sort.by("fullName").ascending());
        Page<User> userPage=userRepository.findAll(pageable);

        List<UserResponse> content=userPage.getContent()
                .stream()
                .map(userMapper::mapToResponse)
                .toList();

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

        checkSelfOrAdmin(user);

        user.setFullName(request.fullName());
        user.setPhone(request.phone());

        return userMapper.mapToResponse(userRepository.save(user));
    }

    public void deleteUser(Long id){
        checkAdmin();

        User user=userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setIsActive(false);
        userRepository.save(user);
    }

    public List<UserResponse> getUsersByRole(Role role){
        checkAdminOrOwner();

        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::mapToResponse)
                .toList();
    }

    public List<UserResponse> searchUsers(String keyword){
        checkAdminOrOwner();

        return userRepository.searchByNameOrEmail(keyword)
                .stream()
                .map(userMapper::mapToResponse)
                .toList();
    }

    public PageResponse<UserResponse> getActiveUsers(int page,int size){
        checkAdminOrOwner();

        Pageable pageable=PageRequest.of(page,size,Sort.by("fullName").ascending());
        Page<User> userPage=userRepository.findByIsActiveTrue(pageable);

        List<UserResponse> content=userPage.getContent()
                .stream()
                .map(userMapper::mapToResponse)
                .toList();

        return new PageResponse<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }

    private void checkSelfOrAdminOrOwner(User user){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String loggedEmail=auth.getName();

        boolean isAdmin=hasAuthority(auth,"ADMIN");
        boolean isOwner=hasAuthority(auth,"OWNER");
        boolean isSelf=user.getEmail().equals(loggedEmail);

        if(!isAdmin && !isOwner && !isSelf){
            throw new AccessDeniedException("You can access only your own data");
        }
    }

    private void checkSelfOrAdmin(User user){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String loggedEmail=auth.getName();

        boolean isAdmin=hasAuthority(auth,"ADMIN");
        boolean isSelf=user.getEmail().equals(loggedEmail);

        if(!isAdmin && !isSelf){
            throw new AccessDeniedException("You can update only your own data");
        }
    }

    private void checkAdminOrOwner(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();

        if(!hasAuthority(auth,"ADMIN") && !hasAuthority(auth,"OWNER")){
            throw new AccessDeniedException("Only admin or owner can access this data");
        }
    }

    private void checkAdmin(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();

        if(!hasAuthority(auth,"ADMIN")){
            throw new AccessDeniedException("Only admin can perform this action");
        }
    }

    private boolean hasAuthority(Authentication auth,String authority){
        return auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }
}