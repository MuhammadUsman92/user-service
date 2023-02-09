package com.muhammadusman92.userservice.repo;

import com.muhammadusman92.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
