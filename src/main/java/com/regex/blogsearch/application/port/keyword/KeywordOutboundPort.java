package com.regex.blogsearch.application.port.keyword;

import com.regex.blogsearch.domain.keyword.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface KeywordOutboundPort {
    Keyword save(Keyword keyword);

    Optional<Keyword> findByKeyword(String keyword);

    Page<Keyword> findAll(Pageable pageable);
}
