package com.regex.blogsearch.exception;

import lombok.Getter;

@Getter
public class BlogSearchException extends RuntimeException{
    private BlogSearchErrorCode blogSearchErrorCode;
    private String detail;

    public BlogSearchException(BlogSearchErrorCode errorCode) {
        super(errorCode.getMessage());
        this.blogSearchErrorCode = errorCode;
        this.detail = errorCode.getMessage();
    }

    public BlogSearchException(BlogSearchErrorCode errorCode, String detail) {
        super(errorCode.getMessage());
        this.blogSearchErrorCode = errorCode;
        this.detail = detail;
    }
}
