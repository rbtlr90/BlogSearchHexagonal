package com.regex.blogsearch.dto;

import com.regex.blogsearch.types.KakaoAPIResponseBody;
import com.regex.blogsearch.types.KakaoAPIResponseMeta;
import com.regex.blogsearch.types.NaverAPIResponseBody;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogSearchDTO {
    private KakaoAPIResponseMeta meta;
    private List<String> documents;

    public static BlogSearchDTO fromKakaoApiResponse(KakaoAPIResponseBody kakaoAPIResponseBody) {
        return BlogSearchDTO.builder()
                .meta(kakaoAPIResponseBody.getMeta())
                .documents(kakaoAPIResponseBody.getDocuments())
                .build();
    }

//    public static BlogSearchDTO fromNaverApiResponse(NaverAPIResponseBody naverAPIResponseBody){
//        return
//    }
}
