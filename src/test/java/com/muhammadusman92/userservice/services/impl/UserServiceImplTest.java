//package com.muhammadusman92.userservice.services.impl;
//
//import com.muhammadusman92.userservice.exception.AccountServiceException;
//import com.muhammadusman92.userservice.model.Role;
//import com.muhammadusman92.userservice.model.User;
//import com.muhammadusman92.userservice.payloads.AccountDto;
//import com.muhammadusman92.userservice.payloads.AccountResponse;
//import com.muhammadusman92.userservice.payloads.UserDto;
//import com.muhammadusman92.userservice.repo.RoleRepo;
//import com.muhammadusman92.userservice.repo.UserRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//
//import static java.time.LocalDateTime.now;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//    @Spy
//    ModelMapper modelMapper;
//    @Mock
//    private UserRepo userRepo;
//    @Mock
//    private RoleRepo roleRepo;
//    @Mock
//    private RestTemplate restTemplate;
//    @InjectMocks
//    private UserServiceImpl userServiceImpl;
//    User user_1=new User(2L,"UserName","UserEmail","UserPassword");
//
//    User user_2=new User(2L,"UserName","UserEmail","UserPassword");
//    User user_3=new User(3L,"UserName","UserEmail","UserPassword");
//    Role role = new Role(1L,"NORMAL_USER");
//    AccountDto accountDto_1 = new AccountDto(1L, "Name", "Type", 50000L, 50000L);
//    @Test
//    void createUser() {
//        given(roleRepo.findById(any(Long.class))).willReturn(Optional.ofNullable(role));
//        given(userRepo.existsUserByEmail(any(String.class))).willReturn(false);
//        given(userRepo.save(any(User.class))).willReturn(user_1);
//        UserDto userDto = modelMapper.map(user_1, UserDto.class);
//        UserDto user = userServiceImpl.createUser(userDto);
//        assertThat(user).usingRecursiveComparison().isEqualTo(userDto);
//    }
//
//    @Test
//    void updateUser() {
//        given(userRepo.findById(any(Long.class))).willReturn(Optional.ofNullable(user_1));
//        given(userRepo.save(any(User.class))).willReturn(user_1);
//        UserDto expected = modelMapper.map(user_1, UserDto.class);
//        UserDto user = userServiceImpl.updateUser(expected,expected.getId());
//        assertThat(user).usingRecursiveComparison().isEqualTo(expected);
//    }
//
//    @Test
//    void getUserById() {
//        AccountResponse accountResponse=AccountResponse.builder()
//                .timeStamp(now())
//                .status(HttpStatus.OK)
//                .statusCode(HttpStatus.OK.value())
//                .message("Account is successfully get")
//                .data(accountDto_1).build();
//        given(userRepo.findById(any(Long.class))).willReturn(Optional.ofNullable(user_1));
//        given(restTemplate.getForObject("http://ACCOUNT-SERVICE/accounts/user/"+user_1.getId(),AccountResponse.class)).willReturn(accountResponse);
//        UserDto expected = modelMapper.map(user_1, UserDto.class);
//        UserDto actual = userServiceImpl.getUserById(user_1.getId());
//        expected.setAccountDto(accountDto_1);
//        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
//    }
//
//    @Test
//    void getUserByIdAccountNotExist() {
//        AccountResponse accountResponse=AccountResponse.builder()
//                .timeStamp(now())
//                .status(HttpStatus.OK)
//                .statusCode(HttpStatus.OK.value())
//                .message("Account is successfully get")
//                .data(accountDto_1).build();
//        given(userRepo.findById(any(Long.class))).willReturn(Optional.ofNullable(user_1));
//        given(restTemplate.getForObject("http://ACCOUNT-SERVICE/accounts/user/"+user_1.getId(),AccountResponse.class)).willThrow(new AccountServiceException());
//        UserDto expected = modelMapper.map(user_1, UserDto.class);
//        assertThrows(AccountServiceException.class,()->
//                userServiceImpl.getUserById(user_1.getId())
//        );
//    }
//
//    @Test
//    void getAllUsers() {
//        List<User> userList = Arrays.asList(user_1,user_2,user_3);
//        given(userRepo.findAll()).willReturn(userList);
//        List<UserDto> actualAllUsers=userServiceImpl.getAllUsers();
//        List<UserDto> expected = userList.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
//        assertThat(actualAllUsers).usingRecursiveComparison().isEqualTo(expected);
//    }
//
//    @Test
//    void deleteUserById() {
//        given(userRepo.findById(any(Long.class))).willReturn(Optional.ofNullable(user_1));
//        doNothing().when(userRepo).delete(user_1);
//        userServiceImpl.deleteUserById(user_1.getId());
//    }
//
//    @Test
//    void findByEmail() {
//        given(userRepo.findByEmail(any(String.class))).willReturn(Optional.ofNullable(user_1));
//        UserDto expected = modelMapper.map(user_1, UserDto.class);
//        UserDto actual = userServiceImpl.findByEmail(user_1.getEmail());
//        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
//    }
//}