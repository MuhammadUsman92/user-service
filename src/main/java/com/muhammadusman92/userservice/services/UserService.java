package com.muhammadusman92.userservice.services;


import com.muhammadusman92.userservice.model.User;
import com.muhammadusman92.userservice.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,String userCNIC);
    UserDto getUserByCNIC(String userCNIC);
    String getUserCNIC(String fingerPrintData);
    List<UserDto> getAllUsers();
    void deleteUserByCNIC(String userCNIC);
}
