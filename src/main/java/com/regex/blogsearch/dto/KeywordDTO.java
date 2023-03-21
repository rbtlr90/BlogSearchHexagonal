package com.regex.blogsearch.dto;

import com.regex.blogsearch.domain.keyword.Keyword;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeywordDTO {
    private String keyword;
    private Integer searchCount;

    public static KeywordDTO fromEntity(Keyword keyword) {
        return KeywordDTO.builder()
                .keyword(keyword.getKeyword())
                .searchCount(keyword.getSearchCount())
                .build();
    }
}
