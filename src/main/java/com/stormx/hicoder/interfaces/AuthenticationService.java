package com.stormx.hicoder.interfaces;

import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.dto.AuthenticationResponse;
import com.stormx.hicoder.dto.RequestResetPasswordDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
     AuthenticationResponse register(AuthenticationRequest request);
     AuthenticationResponse authenticate(AuthenticationRequest request);
     AuthenticationResponse getNewAccessToken(HttpServletRequest request, HttpServletResponse response);

    void sendEmailResetPassword(RequestResetPasswordDTO requestResetPasswordDTO);
//    boolean verifyAndChangePwd();
}
