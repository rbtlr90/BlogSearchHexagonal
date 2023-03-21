package com.regex.blogsearch.dto;

import com.regex.blogsearch.exception.BlogSearchErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogSearchErrorResponse {
    private BlogSearchErrorCode errorCode;
    private String errorMessage;
}
