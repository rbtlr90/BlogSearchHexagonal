package com.regex.blogsearch.adapter.controller.keyword;

import com.regex.blogsearch.application.port.keyword.KeywordUsecase;
import com.regex.blogsearch.dto.KeywordDTO;
import com.regex.blogsearch.types.KeywordSortOrder;
import com.regex.blogsearch.domain.keyword.Keyword;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "${apiPrefix}/keyword")
@Validated
public class KeywordController {
    private final KeywordUsecase keywordUsecase;

    @GetMapping("/favorites")
    public List<KeywordDTO> getMostSearchedKeywords(
            @Valid @RequestParam(defaultValue = "1") int page,
            @Valid @RequestParam(defaultValue = "10") final int size,
            @Valid @RequestParam(defaultValue = "DESC") final KeywordSortOrder sortOrder
    ) {
        page = page - 1;
        return this.keywordUsecase.getMostSearchedKeywords(page, size, sortOrder);
    };
}
