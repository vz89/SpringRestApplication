package com.vz89.hometask.controller;

import com.google.gson.Gson;
import com.vz89.hometask.model.Account;
import com.vz89.hometask.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    Account account1;
    Account account2;
    List<Account> accountList;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @BeforeEach
    void init() {
        account1 = new Account(1L, "123");
        account2 = new Account(2L, "fn");
        accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldReturnAccountsWithRoleAdmin() throws Exception {
        given(accountService.getAccounts()).willReturn(accountList);
        mockMvc.perform(get("/accounts")).andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldCreateAccountsWithRoleAdmin() throws Exception {
        Gson gson = new Gson();
        String accountGson = gson.toJson(new Account(1L,"new"));
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountGson))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "moderator", roles = {"MODERATOR"})
    @Test
    void shouldReturnAccountsWithRoleModerator() throws Exception {
        given(accountService.getAccounts()).willReturn(accountList);
        mockMvc.perform(get("/accounts")).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/accounts"})
    void shouldReturnAccountsWithRoleUser(String value) throws Exception {
        given(accountService.getAccounts()).willReturn(accountList);
        mockMvc.perform(get(value)).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/accounts", "/accounts/1"})
    void shouldForbiddenCreateAccountsByUser(String value) throws Exception {
        mockMvc.perform(post(value)).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/accounts", "/accounts/1"})
    void shouldForbiddenEditAccountsByUser(String value) throws Exception {
        mockMvc.perform(put(value)).andExpect(status().isForbidden());
    }
    @WithMockUser(username = "user", roles = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/accounts", "/accounts/1"})
    void shouldForbiddenDeleteAccountsByUser(String value) throws Exception {
        mockMvc.perform(delete(value)).andExpect(status().isForbidden());
    }


}
