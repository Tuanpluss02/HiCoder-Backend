package com.stormx.hicoder.services;

import org.thymeleaf.context.Context;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithHtml(String to, String subject, String templateName, Context context);
}
