package com.example.demo.api;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.Service.IUserService;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    private UserDTO userDTO;
    private UserResponse userResponse;
    private LocalDate date;

    @BeforeEach
    void initData() {
        date = LocalDate.of(2003, 1, 1);
        userDTO = UserDTO.builder()
                .username("vanmanh")
                .firstname("Manh")
                .lastname("Nguyen")
                .password("123456789")
                .dob(date)
                .build();
        userResponse = UserResponse.builder()
                .id(5)
                .username("vanmanh")
                .firstname("Manh")
                .lastname("Nguyen")
                .dob(date)
                .build();
    }

    @Test
    // Test Success
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(userDTO);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        // When. then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
    }
}
