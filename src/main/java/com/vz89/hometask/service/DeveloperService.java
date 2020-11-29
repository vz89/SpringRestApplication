package com.vz89.hometask.service;

import com.vz89.hometask.model.Developer;

import java.util.List;

public interface DeveloperService {
    List<Developer> getDevelopers();

    void save(Developer developer);

    boolean update(Long id, Developer developer);

    void delete(Developer developer);

    Developer findById(Long id);
}
