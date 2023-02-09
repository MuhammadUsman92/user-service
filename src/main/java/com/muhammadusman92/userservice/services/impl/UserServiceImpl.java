package com.muhammadusman92.userservice.services.impl;

import com.muhammadusman92.userservice.config.AppConstants;
import com.muhammadusman92.userservice.exception.AccountServiceException;
import com.muhammadusman92.userservice.exception.AlreadyExistExeption;
import com.muhammadusman92.userservice.exception.ResourceNotFoundException;
import com.muhammadusman92.userservice.model.Role;
import com.muhammadusman92.userservice.model.User;
import com.muhammadusman92.userservice.payloads.AccountDto;
import com.muhammadusman92.userservice.payloads.AccountResponse;
import com.muhammadusman92.userservice.payloads.Response;
import com.muhammadusman92.userservice.payloads.UserDto;
import com.muhammadusman92.userservice.repo.RoleRepo;
import com.muhammadusman92.userservice.repo.UserRepo;
import com.muhammadusman92.userservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
public class UserServiceImpl implements UserService {
    private static final String ACCOUNT_SERVICE = "accountService";
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        if(userRepo.existsUserByEmail(user.getEmail())){
            throw new AlreadyExistExeption("email",user.getEmail());
        }
        Role role = roleRepo.findById(AppConstants.NORMAL_USER)
                        .orElseThrow(()->new ResourceNotFoundException("Role","Id",AppConstants.NORMAL_USER));
        user.getRoles().add(role);
        user.setPassword(user.getPassword());
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User findUser = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        User user = modelMapper.map(userDto, User.class);
        user.setId(findUser.getId());
        user.setRoles(findUser.getRoles());
        user.setEmail(findUser.getEmail());
        user.setPassword(user.getPassword());
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User findUser = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        UserDto userDto = modelMapper.map(findUser, UserDto.class);
        userDto.setAccountDto(getUserAccount(userDto.getId()));
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long userId) {
        User findUser = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        userRepo.delete(findUser);
    }

    @Override
    public UserDto findByEmail(String email) {
        User findUser = userRepo.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",email));
        return modelMapper.map(findUser,UserDto.class);
    }


    @CircuitBreaker(name = ACCOUNT_SERVICE, fallbackMethod = "accountServiceFallback")
    @Retry(name = ACCOUNT_SERVICE)
    @RateLimiter(name = ACCOUNT_SERVICE)
    public AccountDto getUserAccount(Long userId){
        AccountResponse accountResponse=restTemplate.getForObject(
                "http://ACCOUNT-SERVICE/accounts/user/"+userId,AccountResponse.class);
        assert accountResponse != null;
        return modelMapper.map(accountResponse.getData(),AccountDto.class);
    }
    public ResponseEntity<Response> accountServiceFallback(AccountServiceException e) {
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(SERVICE_UNAVAILABLE)
                .statusCode(SERVICE_UNAVAILABLE.value())
                .message("Account Service is taking longer than Expected. Please try again later")
                .build()
                ,SERVICE_UNAVAILABLE);
    }public ResponseEntity<Response> accountServiceFallback(IllegalStateException ex) {
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(SERVICE_UNAVAILABLE)
                .statusCode(SERVICE_UNAVAILABLE.value())
                .message("Account Service is taking longer than Expected. Please try again later")
                .build()
                ,SERVICE_UNAVAILABLE);
    }
}
