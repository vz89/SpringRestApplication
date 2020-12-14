package com.vz89.hometask.controller;

import com.google.gson.Gson;
import com.vz89.hometask.model.Account;
import com.vz89.hometask.model.Developer;
import com.vz89.hometask.service.DeveloperService;
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
class DeveloperControllerTest {
    Developer developer1;
    Developer developer2;
    List<Developer> developers;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeveloperService developerService;


    @BeforeEach
    void init() {
        developer1 = new Developer(1L, "fn", "ln", "spec", new Account(1L, "acc"), null);
        developer2 = new Developer(2L, "fn", "ln", "spec", new Account(1L, "acc"), null);
        developers = new ArrayList<>();
        developers.add(developer1);
        developers.add(developer2);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldReturnDevelopersWithRoleAdmin() throws Exception {
        given(developerService.getDevelopers()).willReturn(developers);
        mockMvc.perform(get("/developers")).andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldCreateDevelopersWithRoleAdmin() throws Exception {
        Gson gson = new Gson();
        String accountGson = gson.toJson(developer1);
        mockMvc.perform(post("/developers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountGson))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "moderator", roles = {"MODERATOR"})
    @Test
    void shouldReturnDevelopersWithRoleModerator() throws Exception {
        given(developerService.getDevelopers()).willReturn(developers);
        mockMvc.perform(get("/developers")).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void shouldReturnDevelopersWithRoleUser() throws Exception {
        given(developerService.getDevelopers()).willReturn(developers);
        mockMvc.perform(get("/developers")).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/developers", "/developers/1"})
    void shouldForbiddenCreateDevelopersByUser(String value) throws Exception {
        mockMvc.perform(post(value)).andExpect(status().isForbidden());
    }
    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/developers", "/developers/1"})
    void shouldForbiddenEditDevelopersByUser(String value) throws Exception {
        mockMvc.perform(put(value)).andExpect(status().isForbidden());
    }
    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/developers", "/developers/1"})
    void shouldForbiddenDeleteDevelopersByUser(String value) throws Exception {
        mockMvc.perform(delete(value)).andExpect(status().isForbidden());
    }


}
