package com.stormx.hicoder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stormx.hicoder.common.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
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
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages  = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers")
    private List<Post> likedPosts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers")
    private List<Comment> likedComments = new ArrayList<>();

    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
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

}