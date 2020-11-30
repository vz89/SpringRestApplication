package com.vz89.hometask.service.smsService;

import java.util.Random;

public class SmsUtils {
    public static Integer generateCode() {
        return new Random().ints(1000, 9999).findFirst().getAsInt();
    }
}
