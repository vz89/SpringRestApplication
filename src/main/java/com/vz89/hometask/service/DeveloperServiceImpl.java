package com.vz89.hometask.service;

import com.vz89.hometask.model.Developer;
import com.vz89.hometask.repo.DeveloperRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepo developerRepo;

    @Override
    public List<Developer> getDevelopers() {
        return developerRepo.findAll();
    }

    @Override
    public void save(Developer developer) {
        developerRepo.save(developer);
    }

    @Override
    public boolean update(Long id, Developer developer) {
        if (developerRepo.findById(id).orElse(null) != null) {
            developer.setId(id);
            developerRepo.save(developer);
            return true;
        } else return false;
    }

    @Override
    public void delete(Developer developer) {
        developerRepo.delete(developer);
    }

    @Override
    public Developer findById(Long id) {
        return developerRepo.findById(id).orElse(null);
    }


}
