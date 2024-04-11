package com.stormx.hicoder.controllers.requests;

import lombok.Builder;

@Builder
public record MessageSend(String to, String message, String from) { }