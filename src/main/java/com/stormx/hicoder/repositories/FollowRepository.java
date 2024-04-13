package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Follow;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {
    boolean existsByFollowerAndFollowing(User followerId, User followingId);
    Follow findByFollowerAndFollowing(User followerId, User followingId);
    Page<Follow> findAllByFollower(User currentUser, Pageable pageable);
    Page<Follow> findAllByFollowing(User targetUser, Pageable pageable);
    List<Follow> findAllByFollowing(User targetUser);
    @Query("SELECT f.following.id FROM Follow f WHERE f.follower = ?1")
    List<String> getFollowingIdsByFollower(User currentUser);
}
