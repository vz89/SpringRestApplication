package com.vz89.hometask.service.impl;

import com.vz89.hometask.dto.UserDTO;
import com.vz89.hometask.model.ActivationCode;
import com.vz89.hometask.model.Role;
import com.vz89.hometask.model.Status;
import com.vz89.hometask.model.User;
import com.vz89.hometask.repo.ActivationCodeRepo;
import com.vz89.hometask.repo.UserRepo;
import com.vz89.hometask.service.UserService;
import com.vz89.hometask.service.smsService.SmsActivationService;
import com.vz89.hometask.service.smsService.SmsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final int VALIDITY_HOURS = 2;
    private final UserRepo userRepo;
    private final SmsActivationService smsActivationService;
    private final ActivationCodeRepo activationCodeRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getUsers() {
        return userRepo.findAll().stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public void create(User user) {
        user.setCreated(LocalDate.now());
        user.setLastPasswordChangeDate(LocalDate.now());
        user.setStatus(Status.APPROVAL_REQUIRED);
        user.setUpdated(LocalDate.now());
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int code = SmsUtils.generateCode();
        smsActivationService.SendSms(user.getPhoneNumber(), code);

        ActivationCode activationCode = new ActivationCode(code, getExpirationDate());
        activationCode.setUser(user);
        userRepo.save(user);
        activationCodeRepo.save(activationCode);
    }

    public boolean activate(User user, Integer code) {
        ActivationCode activationCode = activationCodeRepo.findByUserId(user.getId());
        if (activationCode.getCode().equals(code) && LocalDateTime.now().isBefore(activationCode.getExpirationDate())) {
            user.setStatus(Status.ACTIVE);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public void getNewActivationCode(User user) {
        ActivationCode activationCode = activationCodeRepo.findByUserId(user.getId());
        int code = SmsUtils.generateCode();
        smsActivationService.SendSms(user.getPhoneNumber(), code);
        activationCode.setCode(code);
        activationCode.setExpirationDate(getExpirationDate());
        activationCodeRepo.save(activationCode);
    }

    @Override
    public boolean update(Long id, User user) {
        User userUpdated = userRepo.findById(id).orElse(null);
        if (userUpdated != null) {
            userUpdated.setPassword(user.getPassword());
            userUpdated.setLastPasswordChangeDate(LocalDate.now());
            userRepo.save(userUpdated);
            return true;
        } else return false;
    }

    @Override
    public void delete(User user) {
        user.setStatus(Status.DELETED);
        user.setUpdated(LocalDate.now());
    }

    @Override
    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        return UserDTO.toDTO(user);
    }

    @Override
    public boolean updateStatusOrRole(Long id, User user) {
        User userUpdated = userRepo.findById(id).orElse(null);
        if (user.getStatus() != null) {
            assert userUpdated != null;
            userUpdated.setStatus(user.getStatus());
        }

        if (user.getRoles() != null) {
            assert userUpdated != null;
            userUpdated.setRoles(user.getRoles());
        }
        userRepo.save(userUpdated);
        return false;
    }

    private LocalDateTime getExpirationDate() {
        return LocalDateTime.now().plusHours(VALIDITY_HOURS);
    }
}
