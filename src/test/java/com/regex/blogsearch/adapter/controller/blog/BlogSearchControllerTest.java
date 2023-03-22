package com.regex.blogsearch.adapter.controller.blog;

import com.regex.blogsearch.application.port.blog.BlogSearchUsecase;
import com.regex.blogsearch.dto.BlogSearchDTO;
import com.regex.blogsearch.types.BlogSortType;
import com.regex.blogsearch.types.KakaoAPIResponseMeta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(BlogSearchController.class)
@AutoConfigureMockMvc(addFilters = false)
class BlogSearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogSearchUsecase blogSearchUsecase;

    private String query = "akjahsldf";
    private int page = 1;
    private int size = 10;
    private BlogSortType blogSortType = BlogSortType.accuracy;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    @Test
    void getBlogs() throws Exception {
        given(blogSearchUsecase.getBlogs(this.page, this.size, this.blogSortType, this.query))
                .willReturn(BlogSearchDTO.builder()
                        .meta(KakaoAPIResponseMeta.builder()
                                .is_end(true)
                                .pageable_count(0)
                                .total_count(0)
                                .build())
                        .documents(Arrays.asList())
                        .build());
        String urlWithQuery = "/api/v1/blogs?query=" + query
                + "&page=" + String.valueOf(page)
                + "&size=" + String.valueOf(size)
                + "&sort=" + blogSortType;
        mockMvc.perform(get(urlWithQuery).contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documents", is(Arrays.asList())))
                .andDo(print());
    }

}