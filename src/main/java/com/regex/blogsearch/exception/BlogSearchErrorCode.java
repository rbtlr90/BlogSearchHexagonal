package com.regex.blogsearch.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlogSearchErrorCode {
    INTERNAL_SERVER_ERROR("Internal server error"),
    INVALID_REQUEST("Invalid request");

    private final String message;
}
