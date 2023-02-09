//package com.muhammadusman92.userservice.controller;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.muhammadusman92.userservice.exception.AccountServiceException;
//import com.muhammadusman92.userservice.model.Role;
//import com.muhammadusman92.userservice.payloads.AccountDto;
//import com.muhammadusman92.userservice.payloads.AccountResponse;
//import com.muhammadusman92.userservice.payloads.UserDto;
//import com.muhammadusman92.userservice.repo.RoleRepo;
//import com.muhammadusman92.userservice.services.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static java.time.LocalDateTime.now;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//class UserControllerIntergrationTest {
//    @Autowired
//    private UserController userController;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleRepo roleRepo;
//    @MockBean
//    private RestTemplate restTemplate;
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper=new ObjectMapper()
//            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,false)
//            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,false)
//            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
//    private Role role=new Role(502L,"ADMIN");
//    @BeforeEach
//    public void setUp() {
//        if(!roleRepo.existsById(502L)){
//            roleRepo.save(role);
//        }
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//    AccountDto accountDto_1 = new AccountDto(1L, "Name", "Type", 50000L, 50000L);
//    AccountDto accountDto_2 = new AccountDto(2L, "Name", "Type", 50000L, 50000L);
//    AccountDto accountDto_3 = new AccountDto(3L, "Name", "Type", 50000L, 50000L);
//
//
//    UserDto userDto_2 = new UserDto(1L,"UserName","UserEmail2","UserPassword",accountDto_2);
//    UserDto userDto_3 = new UserDto(1L,"UserName","UserEmail3","UserPassword",accountDto_3);
//
//    @Test
//    void createUser() throws Exception {
//        UserDto userDto_1 = new UserDto(1L,"UserName","UserEmailCreate","UserPassword",accountDto_1);
//        String expected = objectMapper.writeValueAsString(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(post("/api/users/")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(expected))
//                        .andExpect(status().isCreated())
//                        .andReturn();
//    }
//
//    @Test
//    void updateUser() throws Exception {
//        UserDto userDto_1 = new UserDto(1L,"UserName","UserEmailUpdate","UserPassword",accountDto_1);
//        String expected = objectMapper.writeValueAsString(userDto_1);
//        UserDto user = userService.createUser(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(put("/api/users/"+user.getId())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(expected))
//                        .andExpect(status().isOk())
//                        .andReturn();
//    }
//
//    @Test
//    void deleteUser() throws Exception {
//        UserDto userDto_1 = new UserDto(1L,"UserName","UserEmailDelete","UserPassword",accountDto_1);
//        UserDto user = userService.createUser(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(delete("/api/users/"+user.getId())
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.message").value("User deleted successfully"))
//                        .andReturn();
//    }
//
//    @Test
//    void getAllUser() throws Exception {
//        List<UserDto> userDtoList = Arrays.asList(userDto_2, userDto_3);
//        String expected = objectMapper.writeValueAsString(userDtoList);
//        MvcResult mvcResult =
//                this.mockMvc.perform(get("/api/users/")
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andReturn();
//    }
//
//    @Test
//    @Disabled
//    void getUserById() throws Exception {
//        UserDto userDto_1 = new UserDto(1L,"UserName","UserEmailID","UserPassword",accountDto_1);
//        UserDto user = userService.createUser(userDto_1);
//        AccountResponse accountResponse=AccountResponse.builder()
//                .timeStamp(now())
//                .status(HttpStatus.OK)
//                .statusCode(HttpStatus.OK.value())
//                .message("Account is successfully get")
//                .data(accountDto_1).build();
//        given(restTemplate.getForObject("http://ACCOUNT-SERVICE/accounts/user/"+user.getId(),AccountResponse.class)).willThrow(new AccountServiceException());
//        String expected = objectMapper.writeValueAsString(user);
//        MvcResult mvcResult =
//                this.mockMvc.perform(get("/api/users/"+user.getId())
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andReturn();
//    }
//
//    @Test
//    void getUserByEmail() throws Exception {
//        UserDto userDto_1 = new UserDto(1L,"UserName","UserByEmail","UserPassword",accountDto_1);
//        String expected = objectMapper.writeValueAsString(userDto_1);
//        UserDto user = userService.createUser(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(get("/api/users/email/"+user.getEmail())
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andReturn();
//    }
//}