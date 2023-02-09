//package com.muhammadusman92.userservice.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.muhammadusman92.userservice.payloads.AccountDto;
//import com.muhammadusman92.userservice.payloads.UserDto;
//import com.muhammadusman92.userservice.services.UserService;
//import com.muhammadusman92.userservice.services.impl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerTest {
//    @Mock
//    private UserServiceImpl userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    AccountDto accountDto_1 = new AccountDto(1L, "Name", "Type", 50000L, 50000L);
//    AccountDto accountDto_2 = new AccountDto(2L, "Name", "Type", 50000L, 50000L);
//    AccountDto accountDto_3 = new AccountDto(3L, "Name", "Type", 50000L, 50000L);
//
//
//    UserDto userDto_1 = new UserDto(1L,"UserName","UserEmail","UserPassword",accountDto_1);
//    UserDto userDto_2 = new UserDto(1L,"UserName","UserEmail","UserPassword",accountDto_2);
//    UserDto userDto_3 = new UserDto(1L,"UserName","UserEmail","UserPassword",accountDto_3);
//
//
//
//
//
//    private ObjectMapper objectMapper=new ObjectMapper()
//            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,false)
//            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,false)
//            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//    @Test
//    void createUser() throws Exception {
//        String expected = objectMapper.writeValueAsString(userDto_1);
//        given(userService.createUser(any(UserDto.class))).willReturn(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(post("/api/users/")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(expected))
//                        .andExpect(status().isCreated())
//                        .andReturn();
//        String json = mvcResult.getResponse().getContentAsString();
//        JsonNode jsonNodeRoot = objectMapper.readTree(json);
//        String actual = jsonNodeRoot.get("data").toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void updateUser() throws Exception {
//        String expected = objectMapper.writeValueAsString(userDto_1);
//        given(userService.updateUser(any(UserDto.class),any(Long.class))).willReturn(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(put("/api/users/"+userDto_1.getId())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(expected))
//                        .andExpect(status().isOk())
//                        .andReturn();
//        String json = mvcResult.getResponse().getContentAsString();
//        JsonNode jsonNodeRoot = objectMapper.readTree(json);
//        String actual = jsonNodeRoot.get("data").toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void deleteUser() throws Exception {
//        doNothing().when(userService).deleteUserById(any(Long.class));
//        MvcResult mvcResult =
//                this.mockMvc.perform(delete("/api/users/"+userDto_1.getId())
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.message").value("User deleted successfully"))
//                        .andReturn();
//    }
//
//    @Test
//    void getAllUser() throws Exception {
//        List<UserDto> userDtoList = Arrays.asList(userDto_1, userDto_2, userDto_3);
//        String expected = objectMapper.writeValueAsString(userDtoList);
//        given(userService.getAllUsers()).willReturn(userDtoList);
//        MvcResult mvcResult =
//                this.mockMvc.perform(get("/api/users/")
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andReturn();
//        String json = mvcResult.getResponse().getContentAsString();
//        JsonNode jsonNodeRoot = objectMapper.readTree(json);
//        String actual = jsonNodeRoot.get("data").toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getUserById() throws Exception {
//        String expected = objectMapper.writeValueAsString(userDto_1);
//        given(userService.getUserById(any(Long.class))).willReturn(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(get("/api/users/"+userDto_1.getId())
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andReturn();
//        String json = mvcResult.getResponse().getContentAsString();
//        JsonNode jsonNodeRoot = objectMapper.readTree(json);
//        String actual = jsonNodeRoot.get("data").toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getUserByEmail() throws Exception {
//        String expected = objectMapper.writeValueAsString(userDto_1);
//        given(userService.findByEmail(any(String.class))).willReturn(userDto_1);
//        MvcResult mvcResult =
//                this.mockMvc.perform(get("/api/users/email/"+userDto_1.getEmail())
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andReturn();
//        String json = mvcResult.getResponse().getContentAsString();
//        JsonNode jsonNodeRoot = objectMapper.readTree(json);
//        String actual = jsonNodeRoot.get("data").toString();
//        assertEquals(expected, actual);
//    }
//}