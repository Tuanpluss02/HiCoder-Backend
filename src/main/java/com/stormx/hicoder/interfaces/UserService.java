package com.stormx.hicoder.interfaces;


import com.stormx.hicoder.entities.User;

public interface UserService {
    User loadUserByUsername(String username);

    User getCurrentUser();
}
