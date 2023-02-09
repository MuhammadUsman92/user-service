package com.muhammadusman92.userservice.repo;

import com.muhammadusman92.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);
}
