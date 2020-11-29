package com.vz89.hometask.service;

import com.vz89.hometask.model.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAccounts();

    void save(Account account);

    boolean update(Long id, Account account);

    void delete(Account account);

}
