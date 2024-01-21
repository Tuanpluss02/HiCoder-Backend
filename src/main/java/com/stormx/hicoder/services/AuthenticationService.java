package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    public AuthenticationResponse register(AuthenticationRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public void refreshToken(HttpServletRequest request,HttpServletResponse response);
}
