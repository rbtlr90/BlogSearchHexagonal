package com.regex.blogsearch.adapter.persistence.keyword;

import com.regex.blogsearch.application.port.keyword.KeywordOutboundPort;
import com.regex.blogsearch.domain.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KeywordAdapter implements KeywordOutboundPort {
    private final KeywordRepository keywordRepository;

    @Override
    public Keyword save(Keyword keyword) {
        return this.keywordRepository.save(keyword);
    };

    @Override
    public Optional<Keyword> findByKeyword(String keyword) {
        return this.keywordRepository.findByKeyword(keyword);
    }

    @Override
    public Page<Keyword> findAll(Pageable pageable) {
        return this.keywordRepository.findAll(pageable);
    };
}
