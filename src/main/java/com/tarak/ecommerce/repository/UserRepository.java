package com.tarak.ecommerce.repository;

import com.tarak.ecommerce.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByUserId(String userId);
    boolean existsByMobile(String mobile);
}