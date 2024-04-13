package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.entities.*;
import com.stormx.hicoder.exceptions.AppException;
import com.stormx.hicoder.services.FCMService;
import com.stormx.hicoder.services.FollowService;
import com.stormx.hicoder.services.NotificationService;
import com.stormx.hicoder.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final FCMService fcmService;
    private final FollowService followService;
    private final TokenService tokenService;

    @Override
    public void newFollowNotification(User userFollowed, User userFollower) {
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(userFollowed);
            fcmService.sendNotificationToManyDevice("New Follower", "Recently, " + userFollower.getUsername() + " followed you. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }
    }

    @Override
    public void newPostNotification(User user) {
       try{
           List<Optional<User>> followers = followService.getAllFollowers(user);
           if (followers.isEmpty()){
               return;
           }
           List<User> users = followers.stream().map(Optional::get).toList();
           List<Token> allDeviceTokens = new ArrayList<>();
           users.forEach(follower -> {
               allDeviceTokens.addAll(tokenService.getAllDeviceTokens(follower)) ;
           });
           if (allDeviceTokens.isEmpty()){
               return;
           }
           fcmService.sendNotificationToManyDevice("New Post", "Recently, " + user.getUsername() + " posted a new post. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
       } catch (Exception e){
          throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
       }

    }

    @Override
    public void newMessageNotification(User user, User sender, Message message){
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(user);
            if (allDeviceTokens.isEmpty()){
                return;
            }
            fcmService.sendNotificationToManyDevice("New Message from " + sender.getUsername(), message.getContent(), allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }
    }

    @Override
    public void newCommentNotification(User userCommented, Post post) {
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(post.getAuthor());
            if (allDeviceTokens.isEmpty()){
                return;
            }
            fcmService.sendNotificationToManyDevice("New Comment", "Recently, " + userCommented.getUsername() + " commented on your post. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }

    }

    @Override
    public void newPostLikeNotification(User user, Post post) {
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(post.getAuthor());
            if (allDeviceTokens.isEmpty()){
                return;
            }
            fcmService.sendNotificationToManyDevice("New Like", "Recently, " + user.getUsername() + " liked your post. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }
    }

    @Override
    public void newCommendLikeNotification(User user, Comment comment) {
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(comment.getAuthor());
            if (allDeviceTokens.isEmpty()){
                return;
            }
            fcmService.sendNotificationToManyDevice("New Like", "Recently, " + user.getUsername() + " liked your comment. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }
    }
}
