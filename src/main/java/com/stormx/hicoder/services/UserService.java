package com.stormx.hicoder.services;


import com.stormx.hicoder.dto.UserDTO;

public interface UserService {
    UserDTO loadUserByUsername(String username);
}
