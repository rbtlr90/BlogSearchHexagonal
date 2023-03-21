package com.regex.blogsearch.application.usecase.keyword;

import com.regex.blogsearch.application.port.keyword.KeywordOutboundPort;
import com.regex.blogsearch.application.port.keyword.KeywordUsecase;
import com.regex.blogsearch.dto.KeywordDTO;
import com.regex.blogsearch.types.KeywordSortOrder;
import com.regex.blogsearch.domain.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService implements KeywordUsecase {
    private final KeywordOutboundPort keywordOutboundPort;

    @Override
    @Transactional(readOnly = true)
    public List<KeywordDTO> getMostSearchedKeywords(
            int page,
            int size,
            KeywordSortOrder sortOrder
    ) {
        Sort sort;
        if (sortOrder == KeywordSortOrder.DESC) {
            sort = Sort.by("searchCount").descending();
        }
        else {
            sort = Sort.by("searchCount").ascending();
        }
        return this.getKeywordList(page, size, sort)
                .stream().map(KeywordDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private List<Keyword> getKeywordList(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Keyword> keywords = this.keywordOutboundPort.findAll(pageable);
        return keywords.getContent();
    }
}
