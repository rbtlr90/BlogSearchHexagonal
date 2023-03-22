package com.regex.blogsearch.application.usecase.keyword;

import com.regex.blogsearch.application.port.keyword.KeywordOutboundPort;
import com.regex.blogsearch.domain.keyword.Keyword;
import com.regex.blogsearch.dto.KeywordDTO;
import com.regex.blogsearch.types.KeywordSortOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class KeywordServiceTest {
    @Mock
    private KeywordOutboundPort keywordOutboundPort;

    @InjectMocks
    private KeywordService keywordService;


    @Test
    void getMostSearchedKeywords() {
        //given
        int page = 1;
        int size = 10;
        KeywordSortOrder sortOrder = KeywordSortOrder.DESC;
        Sort sort = Sort.by("searchCount").descending();


        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Keyword> keywords = Mockito.mock(Page.class);
        given(keywordOutboundPort.findAll(pageable))
                .willReturn(keywords);

        //when
        List<KeywordDTO> keywordDTOs = keywordService.getMostSearchedKeywords(page, size, sortOrder);

        //then
        assertEquals(keywordDTOs,
                keywords.stream().map(KeywordDTO::fromEntity).collect(Collectors.toList()));
    }

}