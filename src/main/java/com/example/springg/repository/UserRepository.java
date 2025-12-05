package com.example.springg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springg.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByUsername(String username);
}
