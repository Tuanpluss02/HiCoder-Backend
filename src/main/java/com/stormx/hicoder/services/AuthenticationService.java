package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
     AuthenticationResponse register(AuthenticationRequest request);
     AuthenticationResponse authenticate(AuthenticationRequest request);
     void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException;
}
