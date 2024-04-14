package com.stormx.hicoder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stormx.hicoder.common.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    @Getter
    private String username;

    @Getter
    @Setter
    private String displayName;

    @Getter
    @Setter
    private String avatarUrl;

    @Getter
    @Setter
    private LocalDate birthday;

    @Getter
    @Setter
    private String about;

    @Column(nullable = false)
    @JsonIgnore
    @Getter
    @Setter
    private String password;

    @Column(nullable = false, unique = true)
    @Getter
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followings;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers", cascade = CascadeType.ALL)
    private List<Post> likedPosts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers", cascade = CascadeType.ALL)
    private List<Comment> likedComments = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> sentConversations;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> receivedConversations;

    @Getter
    @Setter
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

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

}