package com.tarak.ecommerce.controller.user;

import com.tarak.ecommerce.dto.request.LoginRequest;
import com.tarak.ecommerce.dto.request.RefreshTokenRequest;
import com.tarak.ecommerce.dto.request.RegisterRequest;
import com.tarak.ecommerce.dto.request.UpdateProfileRequest;
import com.tarak.ecommerce.dto.response.LoginResponse;
import com.tarak.ecommerce.dto.response.UserResponse;
import com.tarak.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid
            @RequestBody RegisterRequest request) {

        String userId =
                userService.register(request);

        return ResponseEntity.ok(
                Map.of(
                        "status", "SUCCESS",
                        "userId", userId
                )
        );
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userService.login(request));
    }
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.getProfile(
                        authentication.getName()));
    }
    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(
                userService.updateProfile(
                        authentication.getName(),
                        request));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                userService.refreshToken(
                        request.getRefreshToken()));
    }
}