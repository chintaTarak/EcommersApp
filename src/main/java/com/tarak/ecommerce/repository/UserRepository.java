package com.tarak.ecommerce.repository;

import com.tarak.ecommerce.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository
        extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}