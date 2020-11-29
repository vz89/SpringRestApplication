package com.vz89.hometask.controller;

import com.vz89.hometask.model.Developer;
import com.vz89.hometask.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping("/developers")
    public ResponseEntity<List<Developer>> getDevelopers() {
        List<Developer> developers = developerService.getDevelopers();
        return developers != null && !developers.isEmpty()
                ? new ResponseEntity<>(developers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/developers/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable("id") Long id) {
        Developer developer = developerService.findById(id);
        return developer != null
                ? new ResponseEntity<>(developer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/developers")
    public ResponseEntity<?> createDeveloper(@RequestBody Developer developer) {
        developerService.save(developer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/developers/{id}")
    public ResponseEntity<?> updateDeveloper(@PathVariable("id") Long id, @RequestBody Developer developer) {
        boolean updated = developerService.update(id, developer);
        return (updated) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/developers/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable("id") Developer developer) {
        developerService.delete(developer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
