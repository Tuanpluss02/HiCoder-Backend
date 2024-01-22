package com.stormx.hicoder.services;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendMail(String to, String subject, String body) throws MessagingException;
}
