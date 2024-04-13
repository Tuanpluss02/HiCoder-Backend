package com.stormx.hicoder.controllers.helpers;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserProfile {
    @Size(min = 5, message = "Display name must be at least 5 characters")
    private String displayName;

    @URL(message = "Invalid URL")
    private String avatarUrl;

    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "Birthday must be in yyyy-MM-dd format"
    )
    private String birthday;

    @Size(min = 5, message = "About must be at least 5 characters")
    private String about;
}
