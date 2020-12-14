package com.vz89.hometask.service.impl;

import com.vz89.hometask.model.ActivationCode;
import com.vz89.hometask.model.Status;
import com.vz89.hometask.model.User;
import com.vz89.hometask.repo.ActivationCodeRepo;
import com.vz89.hometask.repo.UserRepo;
import com.vz89.hometask.service.UserService;
import com.vz89.hometask.service.smsService.SmsActivationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepo userRepo;

    @MockBean
    ActivationCodeRepo activationCodeRepo;

    @MockBean
    SmsActivationService smsActivationService;

    @BeforeEach
    void init() {

    }

    @Test
    void shouldCreateUser() {
        User user = new User("username", "123");
        userService.create(user);
        assertAll(
                () -> assertEquals("username", user.getUsername()),
                () -> assertNotNull(user.getCreated()),
                () -> assertNotNull(user.getUpdated()),
                () -> assertNotNull(user.getLastPasswordChangeDate()),
                () -> assertEquals(Status.APPROVAL_REQUIRED, user.getStatus())
        );
    }

    @Test
    void shouldActivateUser() {
        User user = new User(1L,"username", "123");
        given(activationCodeRepo.findByUserId(1L)).willReturn(new ActivationCode(123, LocalDateTime.now().plusHours(2)));
        final boolean activate = userService.activate(user, 123);
        assertTrue(activate);
    }
    @Test
    void shouldNotActivateUser() {
        User user = new User(1L,"username", "123");
        given(activationCodeRepo.findByUserId(1L)).willReturn(new ActivationCode(123, LocalDateTime.now().minusHours(1)));
        final boolean activate = userService.activate(user, 123);
        assertFalse(activate);
    }

}