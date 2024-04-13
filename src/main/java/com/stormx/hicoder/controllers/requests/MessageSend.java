package com.stormx.hicoder.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

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