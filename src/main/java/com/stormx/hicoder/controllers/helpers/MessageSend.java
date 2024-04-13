package com.stormx.hicoder.controllers.helpers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MessageSend {
    @NotBlank(message = "Sender is required")
    @NotEmpty(message = "Sender is required")
    String sender;

    @NotBlank(message = "Content is required")
    String content;

    @NotBlank(message = "Receiver is required")
    @NotEmpty(message = "Receiver is required")
    String receiver;
}