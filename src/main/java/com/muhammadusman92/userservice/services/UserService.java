package com.muhammadusman92.userservice.services;


import com.muhammadusman92.userservice.model.User;
import com.muhammadusman92.userservice.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,Long userId);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    void deleteUserById(Long userId);

    UserDto findByEmail(String email);
}
