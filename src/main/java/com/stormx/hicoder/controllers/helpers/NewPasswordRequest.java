package com.stormx.hicoder.controllers.helpers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewPasswordRequest {
    @NotBlank(message = "New password is required")
    @NotNull(message = "New password is required")
    @Size(min = 6, message = "New password must be at least 6 characters long")
    private String password;
}
