package com.vz89.hometask.repo;

import com.vz89.hometask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
