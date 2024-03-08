package com.stormx.hicoder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stormx.hicoder.common.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User  implements UserDetails {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    @Getter
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    @Getter
    private String password;

    @Column(nullable = false, unique = true)
    @Getter
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages  = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessages = new ArrayList<>();


    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers", cascade = CascadeType.ALL)
    private List<Post> likedPosts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers", cascade = CascadeType.ALL)
    private List<Comment> likedComments = new ArrayList<>();

    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }

    public boolean isPostedBy(Post post) {
        return this.posts.contains(post);
    }

    public void addLikedPost(Post post) {
        this.likedPosts.add(post);
    }

    public void removeLikedPost(Post post) {
        this.likedPosts.remove(post);
    }

    public boolean isLikedPost(Post post) {
        return this.likedPosts.contains(post);
    }

    public void addLikedComment(Comment comment) {
        this.likedComments.add(comment);
    }

    public void removeLikedComment(Comment comment) {
        this.likedComments.remove(comment);
    }

    public boolean isLikedComment(Comment comment) {
        return this.likedComments.contains(comment);
    }

    public void addSentMessage(Message message) {
        this.sentMessages.add(message);
    }

    public void removeSentMessage(Message message) {
        this.sentMessages.remove(message);
    }

    public boolean isSentMessage(Message message) {
        return this.sentMessages.contains(message);
    }

    public void addReceivedMessage(Message message) {
        this.receivedMessages.add(message);
    }

    public void removeReceivedMessage(Message message) {
        this.receivedMessages.remove(message);
    }

    public boolean isReceivedMessage(Message message) {
        return this.receivedMessages.contains(message);
    }
}