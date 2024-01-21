package com.stormx.hicoder.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormx.hicoder.common.ErrorResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmptyRequestBodyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if ("POST".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod()) || "PUT".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod()) || "PATCH".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())) {
            if (servletRequest.getContentLength() == 0) {
                servletResponse.setContentType("application/json");
                servletResponse.setCharacterEncoding("UTF-8");
                ((HttpServletResponse) servletResponse).setStatus(400);
                new ObjectMapper().writeValue(servletResponse.getOutputStream(), new ErrorResponse(HttpStatus.BAD_REQUEST, "Request body is empty", null));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}