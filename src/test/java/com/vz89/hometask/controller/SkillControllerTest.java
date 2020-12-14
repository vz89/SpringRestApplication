package com.vz89.hometask.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vz89.hometask.model.Skill;
import com.vz89.hometask.service.SkillService;
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
class SkillControllerTest {
    Skill skill1;
    Skill skill2;
    List<Skill> skills;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    @BeforeEach
    void init() {
        skill1 = new Skill(1L, "PHP");
        skill2 = new Skill(2L, "HTML");
        skills = new ArrayList<>();
        skills.add(skill1);
        skills.add(skill2);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldReturnSkillsWithRoleAdmin() throws Exception {
        given(skillService.getSkills()).willReturn(skills);
        mockMvc.perform(get("/skills")).andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldCreateSkillWithRoleAdmin() throws Exception {
        Gson gson = new Gson();
        String skillGson = gson.toJson(skill1);
        mockMvc.perform(post("/skills")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(skillGson))
                .andExpect(status().isCreated());
    }


    @WithMockUser(username = "moderator", roles = {"MODERATOR"})
    @Test
    void shouldReturnSkillsWithRoleModerator() throws Exception {
        given(skillService.getSkills()).willReturn(skills);
        mockMvc.perform(get("/skills")).andExpect(status().isOk());
    }

    @WithMockUser(username = "moderator", roles = {"MODERATOR"})
    @Test
    void shouldCreateSkillWithRoleModerator() throws Exception {
        Gson gson = new Gson();
        String skillGson = gson.toJson(skill1);
        mockMvc.perform(post("/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(skillGson))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void shouldReturnSkillsWithRoleUser() throws Exception {
        given(skillService.getSkills()).willReturn(skills);
        mockMvc.perform(get("/skills")).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/skills", "/skills/1"})
    void shouldForbiddenCreateSkillByUser(String value) throws Exception {
        mockMvc.perform(post(value)).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/skills", "/skills/1"})
    void shouldForbiddenEditSkillsByUser(String value) throws Exception {
        mockMvc.perform(put(value)).andExpect(status().isForbidden());
    }
    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/skills", "/skills/1"})
    void shouldForbiddenDeleteSkillByUser(String value) throws Exception {
        mockMvc.perform(delete(value)).andExpect(status().isForbidden());
    }

}
