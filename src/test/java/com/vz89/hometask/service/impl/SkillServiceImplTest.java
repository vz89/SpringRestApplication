package com.vz89.hometask.service.impl;

import com.vz89.hometask.model.Skill;
import com.vz89.hometask.repo.SkillRepo;
import com.vz89.hometask.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class SkillServiceImplTest {
    Skill skill1;
    Skill skill2;
    List<Skill> skills;

    @Autowired
    SkillService skillService;

    @MockBean
    SkillRepo skillRepo;

    @BeforeEach
    void init() {
        skill1 = new Skill(1L, "PHP");
        skill2 = new Skill(2L, "HTML");
        skills = new ArrayList<>();
        skills.add(skill1);
        skills.add(skill2);
    }

    @Test
    void shouldFindAllSkills() {
        Mockito.when(skillRepo.findAll()).thenReturn(skills);
        assertAll(
                () -> assertEquals("PHP", skills.get(0).getName()),
                () -> assertEquals("HTML", skills.get(1).getName())
        );
    }

    @Test
    void shouldGetSkillById() {
        Mockito.when(skillRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(skill1));
        assertEquals("PHP", skill1.getName());
    }

}