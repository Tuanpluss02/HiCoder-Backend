package com.stormx.hicoder.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewPostDTO {
    @NotBlank(message = "Title is required")
    @NotEmpty(message = "Title is required")
    @NotNull(message = "Title is required")
    @Size(min = 5, message = "Title must be at least 5 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @NotEmpty(message = "Content is required")
    @NotNull(message = "Content is required")
    @Size(min = 10, message = "Content must be at least 10 characters")
    private String content;
}
