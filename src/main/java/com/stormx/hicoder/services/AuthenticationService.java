package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.helpers.AuthenticationRequest;
import com.stormx.hicoder.controllers.helpers.AuthenticationResponse;
import com.stormx.hicoder.controllers.helpers.ResetPasswordRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
     AuthenticationResponse register(AuthenticationRequest request);
     AuthenticationResponse authenticate(AuthenticationRequest request);
     AuthenticationResponse getNewAccessToken(HttpServletRequest request, HttpServletResponse response);

    void sendEmailResetPassword(ResetPasswordRequest resetPasswordRequest);
//    boolean verifyAndChangePwd();
}
