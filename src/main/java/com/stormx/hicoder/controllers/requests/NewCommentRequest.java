package com.stormx.hicoder.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewCommentRequest {
    @NotBlank(message = "PostId is required")
    @NotEmpty(message = "PostId is required")
    private String postId;

    @NotBlank(message = "Content is required")
    @NotEmpty(message = "Content is required")
    @Size(min = 1, message = "Content must be at least 1 characters")
    private String content;
}
