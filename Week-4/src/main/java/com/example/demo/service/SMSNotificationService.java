package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("smsService")
public class SMSNotificationService implements NotificationService {

    @Override
    public String send() {
        return "SMS Notification Sent";
    }
}