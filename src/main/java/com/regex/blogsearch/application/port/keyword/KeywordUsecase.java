package com.regex.blogsearch.application.port.keyword;

import com.regex.blogsearch.dto.KeywordDTO;
import com.regex.blogsearch.types.KeywordSortOrder;

import java.util.List;

public interface KeywordUsecase {
    List<KeywordDTO> getMostSearchedKeywords(
            int page,
            int size,
            KeywordSortOrder sortOrder
    );
}
