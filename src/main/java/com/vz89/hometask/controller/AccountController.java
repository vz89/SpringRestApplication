package com.vz89.hometask.controller;

import com.vz89.hometask.model.Account;
import com.vz89.hometask.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        return accounts != null && !accounts.isEmpty()
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Account account) {
        return account != null
                ? new ResponseEntity<>(account, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        accountService.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") Long id, @RequestBody Account account) {
        boolean updated = accountService.update(id, account);
        return (updated) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Account account) {
        accountService.delete(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
