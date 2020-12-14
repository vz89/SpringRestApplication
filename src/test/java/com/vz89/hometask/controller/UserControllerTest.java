package com.vz89.hometask.controller;

import com.google.gson.Gson;
import com.vz89.hometask.dto.UserDTO;
import com.vz89.hometask.model.User;
import com.vz89.hometask.repo.UserRepo;
import com.vz89.hometask.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    User user1;
    User user2;
    List<User> users;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepo userRepo;

    @BeforeEach
    void init() {
        user1 = new User(1L, "user1", "123");
        user2 = new User(2L, "user2", "fn");
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldReturnUsersWithRoleAdmin() throws Exception {
        UserDTO userDTO1 = UserDTO.toDTO(user1);
        UserDTO userDTO2 = UserDTO.toDTO(user1);
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userDTO1);
        userDTOS.add(userDTO2);
        given(userService.getUsers()).willReturn(userDTOS);
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldCreateUsersWithRoleAdmin() throws Exception {
        Gson gson = new Gson();
        String userGson = gson.toJson(new User(1L, "gsonUser", "password"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userGson))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldUpdateUserWithRoleAdmin() throws Exception {
        Gson gson = new Gson();
        User user = new User(1L, "gsonUser", "password");
        String userGson = gson.toJson(user);
        given(userService.update(1L,user)).willReturn(true);
        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userGson))
                .andExpect(status().isOk());
    }
   /* @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldDeleteUserWithRoleAdmin() throws Exception {
       *//* Gson gson = new Gson();
        User user = new User(1L, "gsonUser", "password");
        String userGson = gson.toJson(user);
        given(userService.update(1L,user)).willReturn(true);*//*
       given(userRepo.findById(1L)).willReturn(java.util.Optional.ofNullable(user1));
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }*/

    @WithMockUser(username = "moderator", roles = {"MODERATOR"})
    @Test
    void shouldReturnUsersWithRoleModerator() throws Exception {
        UserDTO userDTO1 = UserDTO.toDTO(user1);
        UserDTO userDTO2 = UserDTO.toDTO(user1);
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userDTO1);
        userDTOS.add(userDTO2);
        given(userService.getUsers()).willReturn(userDTOS);
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @WithMockUser(username = "moderator", roles = {"MODERATOR"})
    @Test
    void shouldForbiddenUpdateUsersWithRoleModerator() throws Exception {
        mockMvc.perform(put("/users/1")).andExpect(status().isForbidden());

    }
    @WithMockUser(username = "moderator", roles = {"MODERATOR"})
    @Test
    void shouldForbiddenDeleteUsersWithRoleModerator() throws Exception {
        mockMvc.perform(delete("/users/1")).andExpect(status().isForbidden());

    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/users", "/users/1"})
    void shouldForbiddenGetUsersWithRoleUser(String value) throws Exception {
        mockMvc.perform(get(value)).andExpect(status().isForbidden());
    }

    @Test
    void shouldForbiddenCreateUsersByUser() throws Exception {
        Gson gson = new Gson();
        String userGson = gson.toJson(new User(1L, "gsonUser", "password"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userGson))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/users", "/users/1"})
    void shouldForbiddenEditUsersByUser(String value) throws Exception {
        mockMvc.perform(put(value)).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/users", "/users/1"})
    void shouldForbiddenDeleteUsersByUser(String value) throws Exception {
        mockMvc.perform(delete(value)).andExpect(status().isForbidden());
    }


}
