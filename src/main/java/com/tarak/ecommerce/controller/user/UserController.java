package com.tarak.ecommerce.controller.user;

import com.tarak.ecommerce.dto.request.RegisterRequest;
import com.tarak.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}