package com.vz89.hometask.controller;


import com.vz89.hometask.model.Skill;
import com.vz89.hometask.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getSkills() {
        List<Skill> skills = skillService.getSkills();
        return skills != null && !skills.isEmpty()
                ? new ResponseEntity<>(skills, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") Skill skill) {
        return skill != null
                ? new ResponseEntity<>(skill, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/skills")
    public ResponseEntity<?> createSkill(@RequestBody Skill skill) {
        skillService.save(skill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable("id") Long id, @RequestBody Skill skill) {
        boolean updated = skillService.update(id, skill);
        return (updated) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable("id") Skill skill) {
        skillService.delete(skill);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
