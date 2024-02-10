package com.stormx.hicoder.dto;

import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.entities.User;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private Role role;
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}