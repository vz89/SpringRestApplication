package com.vz89.hometask.service;

import com.vz89.hometask.model.Skill;

import java.util.List;

public interface SkillService {

    Skill getSkillById(Long id);
    List<Skill> getSkills();

    void save(Skill skill);

    boolean updateSkill(Long id, Skill skill);

    void delete(Skill skill);

}
