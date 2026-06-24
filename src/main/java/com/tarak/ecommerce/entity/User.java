package com.tarak.ecommerce.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String userId;

    private String name;

    private String email;

    private String mobile;

    private String password;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}