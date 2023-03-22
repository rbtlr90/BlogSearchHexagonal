package com.regex.blogsearch.types;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class KakaoAPIResponseMeta {
    private Boolean is_end;
    private Integer pageable_count;
    private Integer total_count;

    public static KakaoAPIResponseMeta fromNaverApiResponse(NaverAPIResponseBody naverAPIResponseBody) {
        boolean is_end;
        int start = naverAPIResponseBody.getStart();
        int display = naverAPIResponseBody.getDisplay();
        int total = naverAPIResponseBody.getTotal();
        int lastPage = Math.floorDiv(total,display);
        if (start + display > 1000) {
            is_end = true;
        }
        else {
            is_end = false;
        }
        return KakaoAPIResponseMeta.builder()
                .is_end(is_end)
                .total_count(total)
                .pageable_count(lastPage <= 1000 ? lastPage : 1000)
                .build();
    }
}
