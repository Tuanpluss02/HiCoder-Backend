package com.stormx.hicoder.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormx.hicoder.common.ErrorResponse;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private boolean isWhiteListed(String servletPath) {
        final String[] WHITE_LIST_URL = {
                "/",
                "/ws",
                "/HiCoder",
                "/api/v1/chat",
                "/api/v1/auth/",
                "/h2-console/",
                "/h2-console",
                "/swagger-resources",
                "/swagger-resources/",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui",
                "/webjars",
                "/api-docs",
                "/v3/api-docs",
        };
        return Arrays.stream(WHITE_LIST_URL).anyMatch(servletPath::startsWith);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (isWhiteListed(servletPath)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null, null));
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), new ErrorResponse(UNAUTHORIZED, "Token is required", servletPath));
            return;
        }
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey.getBytes())).build().verify(authorizationHeader.substring("Bearer ".length()));
            String username = decodedJWT.getSubject();
            userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("Token is invalid"));
            Collection<SimpleGrantedAuthority> authorities = Arrays.stream(decodedJWT.getClaim("roles").asArray(String.class)).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
            response.setContentType(APPLICATION_JSON_VALUE);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), new ErrorResponse(UNAUTHORIZED, e.getMessage(), servletPath));
        }
    }
}