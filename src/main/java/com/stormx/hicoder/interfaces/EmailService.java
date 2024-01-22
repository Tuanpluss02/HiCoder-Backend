package com.stormx.hicoder.interfaces;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendMail(String to, String subject, String body) throws MessagingException;
}
