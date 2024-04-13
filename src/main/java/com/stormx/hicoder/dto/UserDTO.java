package com.stormx.hicoder.dto;

import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.entities.User;
import lombok.*;
import org.modelmapper.ModelMapper;


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
    private String displayName;
    private String avatarUrl;
    private String about;
    private String birthday;

    public static UserDTO fromUser(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }
}