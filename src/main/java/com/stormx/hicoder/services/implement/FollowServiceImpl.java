package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.entities.Follow;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.FollowRepository;
import com.stormx.hicoder.repositories.UserRepository;
import com.stormx.hicoder.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public void followOperation(User currentUser, User targetUser) {
        if (currentUser.equals(targetUser)) {
            throw new BadRequestException("Cannot follow yourself");
        }
        if (followRepository.existsByFollowerAndFollowing(currentUser, targetUser)) {
            throw new BadRequestException("Already following");
        }
        followRepository.save(new Follow(currentUser, targetUser));
    }

    @Override
    public void unfollowOperation(User currentUser, User targetUser) {
        if (currentUser.equals(targetUser)) {
            throw new BadRequestException("Cannot unfollow yourself");
        }
        Follow follow = followRepository.findByFollowerAndFollowing(currentUser, targetUser);
        if (follow == null) {
            throw new BadRequestException("Not following");
        }
        followRepository.delete(follow);
    }

    @Override
    public Page<User> getAllFollowings(User currentUser, Pageable pageable) {
        Page<Follow> followings = followRepository.findAllByFollower(currentUser, pageable);
        return followings.map(Follow::getFollowing);
    }

    @Override
    public Page<User> getAllFollowers(User currentUser, Pageable pageable) {
        Page<Follow> followers = followRepository.findAllByFollowing(currentUser, pageable);
        return followers.map(Follow::getFollower);
    }

    @Override
    public List<Optional<User>> getAllFollowers(User currentUser) {
List<String> userIds = followRepository.getFollowingIdsByFollower(currentUser);
        return userIds.stream().map(userRepository::findById).collect(Collectors.toList());}
}
