package com.tarak.ecommerce.service.user;

import com.tarak.ecommerce.dto.request.RegisterRequest;

public interface UserService {

    String register(RegisterRequest request);
}