package com.vz89.hometask.repo;

import com.vz89.hometask.model.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationCodeRepo extends JpaRepository<ActivationCode, Long> {
    ActivationCode findByUserId(Long user_id);
}
