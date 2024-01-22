package com.stormx.hicoder.dto;

import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private Role role;
    private List<Post> userPosts = new ArrayList<>();
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.userPosts = user.getPosts();
    }
}