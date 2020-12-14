package com.vz89.hometask.repo;

import com.vz89.hometask.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    @EntityGraph("roles_entity_graph")
    User findByUsername(String name);

    @EntityGraph("roles_entity_graph")
    List<User> findAll();

    @EntityGraph("roles_entity_graph")
    Optional<User> findById(Long id);
}
