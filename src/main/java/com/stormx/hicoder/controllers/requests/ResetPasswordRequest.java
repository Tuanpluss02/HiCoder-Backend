package com.stormx.hicoder.controllers.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordRequest {
    @Email
    @NotNull(message = "Email is require")
    @NotBlank(message = "Email is require")
    private String email;
}
