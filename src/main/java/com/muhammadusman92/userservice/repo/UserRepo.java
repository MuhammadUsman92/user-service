package com.muhammadusman92.userservice.repo;

import com.muhammadusman92.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {
    @Query(value = "SELECT COUNT(CNIC) FROM User WHERE CNIC =:cnic",nativeQuery=true)
    long existsCNIC(@Param("cnic") String cnic);
}
