package com.regex.blogsearch.application.usecase.blog;

import com.regex.blogsearch.application.port.blog.BlogSearchOutboundPort;
import com.regex.blogsearch.application.port.blog.BlogSearchUsecase;
import com.regex.blogsearch.application.port.keyword.KeywordOutboundPort;
import com.regex.blogsearch.types.BlogSortType;
import com.regex.blogsearch.domain.keyword.Keyword;
import lombok.RequiredArgsConstructor;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogSearchService implements BlogSearchUsecase {
    private final KeywordOutboundPort keywordOutboundPort;
    private final BlogSearchOutboundPort blogSearchOutboundPort;

    @Transactional
    public String getBlogs(int page, int size, BlogSortType sort, String query) {
        String responseBody = this.blogSearchOutboundPort.getBlogs(page, size, sort, query);
        this.updateSearchCount(query);
        return responseBody;
    };

    @Transactional
    @Synchronized
    private void updateSearchCount(String query) {
        Keyword keyword = this.keywordOutboundPort.findByKeyword(query)
                .orElseGet(() -> Keyword.builder().
                        keyword(query).
                        searchCount(0).
                        build()
                );
        keyword.setSearchCount(keyword.getSearchCount() + 1);
        this.keywordOutboundPort.save(keyword);
    };
}
