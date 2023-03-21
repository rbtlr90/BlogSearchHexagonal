package com.regex.blogsearch.adapter.persistence.keyword;

import com.regex.blogsearch.domain.keyword.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository
        extends JpaRepository<Keyword, Long> {
    Keyword save(Keyword keyword);
    Optional<Keyword> findByKeyword(String keyword);
    Page<Keyword> findAll(Pageable pageable);
}

