package com.tarak.ecommerce.service.user;
import com.tarak.ecommerce.dto.request.LoginRequest;
import com.tarak.ecommerce.dto.request.RegisterRequest;
import com.tarak.ecommerce.dto.request.UpdateProfileRequest;
import com.tarak.ecommerce.dto.response.LoginResponse;
import com.tarak.ecommerce.dto.response.UserResponse;

public interface UserService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getProfile(String email);

    UserResponse updateProfile(String email, UpdateProfileRequest request);

    LoginResponse refreshToken(String refreshToken);
}