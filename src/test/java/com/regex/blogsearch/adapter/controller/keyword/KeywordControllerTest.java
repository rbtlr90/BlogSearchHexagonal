package com.regex.blogsearch.adapter.controller.keyword;

import com.regex.blogsearch.application.port.keyword.KeywordUsecase;
import com.regex.blogsearch.types.KeywordSortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(KeywordController.class)
@AutoConfigureMockMvc(addFilters = false)
class KeywordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeywordUsecase keywordUsecase;

    private int page = 1;
    private int size = 10;
    private KeywordSortOrder sortOrder = KeywordSortOrder.DESC;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    @Test
    void getKeywordsWhenEmpty() throws Exception {
        given(keywordUsecase.getMostSearchedKeywords(this.page, this.size, this.sortOrder))
                .willReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/keyword/favorites").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andDo(print());
    }
}