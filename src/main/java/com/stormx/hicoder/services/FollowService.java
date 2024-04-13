package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface FollowService {
    void followOperation(User currentUser, User targetUser);
    void unfollowOperation(User currentUser, User targetUser);
    Page<User> getAllFollowings(User currentUser, Pageable pageable);
    Page<User> getAllFollowers(User currentUser, Pageable pageable);

    List<Optional<User>> getAllFollowers(User currentUser);
}
