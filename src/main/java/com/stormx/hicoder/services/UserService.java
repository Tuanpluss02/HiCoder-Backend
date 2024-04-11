package com.stormx.hicoder.services;


import com.stormx.hicoder.entities.User;

public interface UserService {
    User loadUserByUsername(String username);
    User getUserById(String id);
    User getCurrentUser();
}
