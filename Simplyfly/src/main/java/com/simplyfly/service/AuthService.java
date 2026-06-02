package com.simplyfly.service;

import com.simplyfly.dto.request.LoginRequest;
import com.simplyfly.dto.request.RegisterRequest;
import com.simplyfly.dto.response.AuthResponse;
import com.simplyfly.model.User;
import com.simplyfly.repository.UserRepository;
import com.simplyfly.util.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already registered");
        }

        User user=User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .role(request.role())
                .isActive(true)
                .build();

        userRepository.save(user);

        String token=jwtUtility.generateToken(user.getEmail(),user.getRole().name());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name(),
                user.getFullName()
        );
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user=userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token=jwtUtility.generateToken(user.getEmail(),user.getRole().name());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name(),
                user.getFullName()
        );
    }
}