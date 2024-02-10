package com.stormx.hicoder.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormx.hicoder.common.ErrorResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
public class EmptyRequestBodyFilter implements Filter {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/user/me",
            "/h2-console",
            "/swagger-resources",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/index.html"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
        System.out.println(servletPath);
        if (Arrays.stream(WHITE_LIST_URL).anyMatch(servletPath::startsWith)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if ("POST".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod()) || "PUT".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod()) || "PATCH".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())) {
            if (servletRequest.getContentLength() == 0) {
                servletResponse.setContentType("application/json");
                servletResponse.setCharacterEncoding("UTF-8");
                ((HttpServletResponse) servletResponse).setStatus(400);
                new ObjectMapper().writeValue(servletResponse.getOutputStream(), new ErrorResponse(HttpStatus.BAD_REQUEST, "Request body is empty", servletPath));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}