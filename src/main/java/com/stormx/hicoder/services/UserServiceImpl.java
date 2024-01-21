package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
