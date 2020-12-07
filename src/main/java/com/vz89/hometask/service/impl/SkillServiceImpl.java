package com.vz89.hometask.service.impl;

import com.vz89.hometask.model.Skill;
import com.vz89.hometask.repo.SkillRepo;
import com.vz89.hometask.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepo skillRepo;

    @Override
    public Skill getSkillById(Long id) {
        return skillRepo.findById(id).orElse(null);
    }

    @Override
    public List<Skill> getSkills() {
        return skillRepo.findAll();
    }

    @Override
    public void save(Skill skill) {
        skillRepo.save(skill);
    }

    @Override
    public boolean update(Long id, Skill skill) {
        if (skillRepo.findById(id).orElse(null) != null) {
            skill.setId(id);
            skillRepo.save(skill);
            return true;
        } else return false;
    }

    @Override
    public void delete(Skill skill) {
        skillRepo.delete(skill);
    }

}
