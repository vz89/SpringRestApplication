package com.vz89.hometask.service;

import com.vz89.hometask.model.ActivationCode;
import com.vz89.hometask.model.Status;
import com.vz89.hometask.model.User;
import com.vz89.hometask.repo.ActivationCodeRepo;
import com.vz89.hometask.repo.UserRepo;
import com.vz89.hometask.service.smsService.SmsActivationService;
import com.vz89.hometask.service.smsService.SmsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final int VALIDITY_HOURS = 2;
    private final UserRepo userRepo;
    private final SmsActivationService smsActivationService;
    private final ActivationCodeRepo activationCodeRepo;

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void create(User user) {
        user.setCreated(LocalDate.now());
        user.setLastPasswordChangeDate(LocalDate.now());
        user.setStatus(Status.APPROVAL_REQUIRED);
        user.setUpdated(LocalDate.now());

        int code = SmsUtils.generateCode();
        smsActivationService.SendSms(user.getPhoneNumber(), code);

        ActivationCode activationCode = new ActivationCode(code, getExpirationDate());
        user.setActivationCode(activationCode);
        activationCode.setUser(user);
        userRepo.save(user);
    }

    public boolean activate(User user, Integer code) {
        ActivationCode activationCode = user.getActivationCode();
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

    private LocalDateTime getExpirationDate() {
        return LocalDateTime.now().plusHours(VALIDITY_HOURS);
    }
}
