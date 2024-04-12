package com.stormx.hicoder.services;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;

public interface NotificationService {
    void newFollowNotification(User userFollowed, User userFollower);
    void newPostNotification(User user) throws FirebaseMessagingException;

    void newCommentNotification(User userCommented, Post post);

    void newPostLikeNotification(User user, Post post);

    void newCommendLikeNotification(User user, Comment comment);
}
