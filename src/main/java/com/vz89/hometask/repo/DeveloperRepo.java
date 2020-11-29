package com.vz89.hometask.repo;

import com.vz89.hometask.model.Developer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeveloperRepo extends JpaRepository<Developer, Long> {

    @EntityGraph("account_skills_entity_graph")
    List<Developer> findAll();

    @EntityGraph("account_skills_entity_graph")
    Optional<Developer> findById(Long id);
}
