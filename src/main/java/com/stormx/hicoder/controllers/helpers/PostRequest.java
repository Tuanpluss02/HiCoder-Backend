package com.stormx.hicoder.controllers.helpers;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRequest {

    @NotBlank(message = "Content is required")
    @NotEmpty(message = "Content is required")
    @Size(min = 5, message = "Content must be at least 5 characters")
    private String content;

    @URL(message = "Invalid URL")
    private String mediaUrl = "";
}
