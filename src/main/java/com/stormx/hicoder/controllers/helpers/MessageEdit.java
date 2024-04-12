package com.stormx.hicoder.controllers.helpers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MessageEdit {
    @NotBlank(message = "messageId is required")
    @NotEmpty(message = "messageId is required")
    String messageId;

    @NotBlank(message = "Content is required")
    String newContent;

}