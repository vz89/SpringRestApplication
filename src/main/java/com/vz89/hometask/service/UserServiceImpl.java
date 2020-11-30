package com.vz89.hometask.service;

import com.vz89.hometask.model.Status;
import com.vz89.hometask.model.User;
import com.vz89.hometask.repo.UserRepo;
import com.vz89.hometask.service.smsService.SmsActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final SmsActivationService smsActivationService;

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
        int activationCode = new Random().ints(1000, 9999).findFirst().getAsInt();
        smsActivationService.SendSms(user.getPhoneNumber(), activationCode);
       // user.setActivationCode(activationCode);
        userRepo.save(user);
    }


}
