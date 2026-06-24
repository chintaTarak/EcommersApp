package com.tarak.ecommerce.service.user.impl;

import com.tarak.ecommerce.dto.request.RegisterRequest;
import com.tarak.ecommerce.entity.User;
import com.tarak.ecommerce.exception.ResourceAlreadyExistsException;
import com.tarak.ecommerce.repository.UserRepository;
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
}