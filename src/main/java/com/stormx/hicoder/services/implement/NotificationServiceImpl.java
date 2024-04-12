package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.common.PaginationInfo;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.Token;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.AppException;
import com.stormx.hicoder.services.FCMService;
import com.stormx.hicoder.services.FollowService;
import com.stormx.hicoder.services.NotificationService;
import com.stormx.hicoder.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.stormx.hicoder.common.Utils.extractToDTO;

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
//           List<User> followers = followService.getAllFollowers(user, PageRequest.of(0, 100)).getContent();
           Page<User> userFollowers = followService.getAllFollowers(user, PageRequest.of(0, 100));
           Pair<PaginationInfo, List<UserDTO>> response = extractToDTO(userFollowers, UserDTO::new);
           List<Token> allDeviceTokens = new ArrayList<>();
//           followers.forEach(follower -> {
//               allDeviceTokens.addAll(tokenService.getAllDeviceTokens(follower)) ;
//           });
//           fcmService.sendNotificationToManyDevice("New Post", "Recently, " + user.getUsername() + " posted a new post. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
       } catch (Exception e){
          throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
       }

    }

    @Override
    public void newCommentNotification(User userCommented, Post post) {
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(post.getAuthor());
            fcmService.sendNotificationToManyDevice("New Comment", "Recently, " + userCommented.getUsername() + " commented on your post. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }

    }

    @Override
    public void newPostLikeNotification(User user, Post post) {
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(post.getAuthor());
            fcmService.sendNotificationToManyDevice("New Like", "Recently, " + user.getUsername() + " liked your post. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }
    }

    @Override
    public void newCommendLikeNotification(User user, Comment comment) {
        try{
            List<Token> allDeviceTokens = tokenService.getAllDeviceTokens(comment.getAuthor());
            fcmService.sendNotificationToManyDevice("New Like", "Recently, " + user.getUsername() + " liked your comment. Let's check it out!", allDeviceTokens.stream().map(Token::getToken).toList());
        } catch (Exception e){
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending notification");
        }
    }
}
