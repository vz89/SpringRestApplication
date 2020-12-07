package com.vz89.hometask.service.impl;

import com.vz89.hometask.model.Account;
import com.vz89.hometask.repo.AccountRepo;
import com.vz89.hometask.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;

    @Override
    public List<Account> getAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public void save(Account account) {
        accountRepo.save(account);
    }

    @Override
    public boolean update(Long id, Account account) {
        if (accountRepo.findById(id).orElse(null) != null) {
            account.setId(id);
            accountRepo.save(account);
            return true;
        } else return false;
    }

    @Override
    public void delete(Account account) {
        accountRepo.delete(account);
    }
}
