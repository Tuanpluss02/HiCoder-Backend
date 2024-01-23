package com.stormx.hicoder.interfaces;


import com.stormx.hicoder.dto.UserDTO;

public interface UserService {
    UserDTO loadUserByUsername(String username);

    UserDTO getCurrentUser();
}
