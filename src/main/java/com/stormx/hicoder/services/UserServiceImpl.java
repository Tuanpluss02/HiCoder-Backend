package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.AppException;
import com.stormx.hicoder.interfaces.UserService;
import com.stormx.hicoder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.UNAUTHORIZED, "User is not authenticated"));
    }


    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        return loadUserByUsername(authentication.getName());
    }
}
