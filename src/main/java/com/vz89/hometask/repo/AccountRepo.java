package com.vz89.hometask.repo;

import com.vz89.hometask.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

}
