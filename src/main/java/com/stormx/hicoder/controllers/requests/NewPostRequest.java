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
public class NewPostRequest {

    @NotBlank(message = "Title is required")
    @NotEmpty(message = "Title is required")
    @Size(min = 5, message = "Title must be at least 5 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @NotEmpty(message = "Title is required")
    @Size(min = 10, message = "Content must be at least 10 characters")
    private String content;
}
