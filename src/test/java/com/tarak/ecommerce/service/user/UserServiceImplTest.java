package com.tarak.ecommerce.service.user;

import com.tarak.ecommerce.dto.request.LoginRequest;
import com.tarak.ecommerce.dto.request.RegisterRequest;
import com.tarak.ecommerce.dto.response.LoginResponse;
import com.tarak.ecommerce.entity.User;
import com.tarak.ecommerce.repository.UserRepository;
import com.tarak.ecommerce.security.JwtService;
import com.tarak.ecommerce.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request = new RegisterRequest();
        request.setName("Tarak");
        request.setEmail("tarak@gmail.com");
        request.setMobile("9876543210");
        request.setPassword("Password@123");

        when(repository.existsByEmail(anyString()))
                .thenReturn(false);

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodedPassword");

        when(repository.save(any(User.class)))
                .thenAnswer(i -> i.getArgument(0));

        String userId = userService.register(request);

        assertNotNull(userId);

        verify(repository, times(1))
                .save(any(User.class));
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();
        request.setEmail("tarak@gmail.com");
        request.setPassword("Password@123");

        User user = User.builder()
                .email("tarak@gmail.com")
                .password("encodedPassword")
                .build();

        when(repository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        when(jwtService.generateToken(anyString()))
                .thenReturn("jwt-token");

        when(jwtService.generateRefreshToken(anyString()))
                .thenReturn("refresh-token");

        LoginResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
    }
}