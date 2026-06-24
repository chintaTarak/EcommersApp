package com.tarak.ecommerce.service.user.impl;

import com.tarak.ecommerce.dto.request.LoginRequest;
import com.tarak.ecommerce.dto.request.RegisterRequest;
import com.tarak.ecommerce.dto.response.LoginResponse;
import com.tarak.ecommerce.entity.User;
import com.tarak.ecommerce.exception.InvalidCredentialsException;
import com.tarak.ecommerce.exception.ResourceAlreadyExistsException;
import com.tarak.ecommerce.repository.UserRepository;
import com.tarak.ecommerce.security.JwtService;
import com.tarak.ecommerce.service.user.UserService;
import com.tarak.ecommerce.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Email already exists");
        }

        User user = User.builder()
                .userId(IdGenerator.generateUserId())
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(user);

        return user.getUserId();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid Email or Password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid Email or Password");
        }

        String token =
                jwtService.generateToken(user.getEmail());

        String refreshToken =
                jwtService.generateRefreshToken(
                        user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}