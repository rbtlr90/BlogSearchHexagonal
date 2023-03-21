package com.regex.blogsearch.types;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoAPIResponseBody {
    private KakaoAPIResponseMeta meta;
    private List<String> documents;
}
