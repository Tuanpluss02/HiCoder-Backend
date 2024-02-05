package com.stormx.hicoder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResetPasswordDTO {
    @Email
    @NotNull(message = "Email is require")
    @NotBlank(message = "Email is require")
    private String email;
}
