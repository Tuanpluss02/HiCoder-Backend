package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.requests.AuthenticationRequest;
import com.stormx.hicoder.controllers.requests.AuthenticationResponse;
import com.stormx.hicoder.controllers.requests.ResetPasswordRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
     AuthenticationResponse register(AuthenticationRequest request);
     AuthenticationResponse authenticate(AuthenticationRequest request);
     AuthenticationResponse getNewAccessToken(HttpServletRequest request, HttpServletResponse response);

    void sendEmailResetPassword(ResetPasswordRequest resetPasswordRequest);
//    boolean verifyAndChangePwd();
}
