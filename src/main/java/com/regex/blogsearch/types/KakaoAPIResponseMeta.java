package com.regex.blogsearch.types;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoAPIResponseMeta {
    private Boolean is_end;
    private Integer pageable_count;
    private Integer total_count;

    public static KakaoAPIResponseMeta fromNaverApiResponse(NaverAPIResponseBody naverAPIResponseBody) {
        boolean is_end;
        int lastPage = Math.floorDiv(naverAPIResponseBody.getTotal(),naverAPIResponseBody.getDisplay());
        if (lastPage == naverAPIResponseBody.getStart()) {
            is_end = true;
        }
        else {
            is_end = false;
        }

        return KakaoAPIResponseMeta.builder()
                .is_end(is_end)
                .total_count(naverAPIResponseBody.getTotal())
                .pageable_count(lastPage <= 100 ? lastPage : 100)
                .build();
    }
}
