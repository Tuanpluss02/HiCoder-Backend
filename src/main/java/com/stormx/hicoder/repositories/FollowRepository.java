package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Follow;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {
    boolean existsByFollowerAndFollowing(User followerId, User followingId);
    Follow findByFollowerAndFollowing(User followerId, User followingId);
    Page<Follow> findAllByFollower(User currentUser, Pageable pageable);
    Page<Follow> findAllByFollowing(User targetUser, Pageable pageable);
    void deleteByFollowerAndFollowing(User currentUser, User targetUser);
}
