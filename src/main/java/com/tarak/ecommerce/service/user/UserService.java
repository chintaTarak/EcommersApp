package com.tarak.ecommerce.service.user;
import com.tarak.ecommerce.dto.request.LoginRequest;
import com.tarak.ecommerce.dto.request.RegisterRequest;
import com.tarak.ecommerce.dto.response.LoginResponse;
public interface UserService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}