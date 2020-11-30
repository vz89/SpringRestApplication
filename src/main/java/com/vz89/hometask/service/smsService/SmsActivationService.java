package com.vz89.hometask.service.smsService;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsActivationService {
    @Value("${twilio.account-sid}")
    private String ACCOUNT_SID = "ACaf1b556ac9d61456464cb6d42174efd5";
    @Value("${twilio.auth-token}")
    private String AUTH_TOKEN = "eb1653174e4fb8ce65a450d37c94a656";
    @Value("${twilio.phone-number}")
    private String PHONE_NUMBER = "+12693715337";

    public void SendSms(String phoneNumber, Integer messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(PHONE_NUMBER), messageBody.toString()).create();
    }


}
