package com.stormx.hicoder.services;


import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.controllers.helpers.UpdateUserProfile;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);
    User loadUserByUsername(String username);
    User getUserById(String id);
    User getCurrentUser();
    void updateUser(User user, UpdateUserProfile updateUserProfile);
    void updateAvatar(User user, String avatarUrl);
    void removeUser(User user);
    void changeRole(User user, Role role);
}
