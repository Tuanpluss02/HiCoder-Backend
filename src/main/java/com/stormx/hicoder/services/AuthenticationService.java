package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.helpers.AuthenticationRequest;
import com.stormx.hicoder.controllers.helpers.AuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
     AuthenticationResponse register(AuthenticationRequest request);
     AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse getNewAccessToken(HttpServletRequest request);
    void sendEmailResetPassword(String email) throws MessagingException;
    void verifyAndChangePwd(String token, String newPassword);
    void logout(HttpServletRequest request);
}
