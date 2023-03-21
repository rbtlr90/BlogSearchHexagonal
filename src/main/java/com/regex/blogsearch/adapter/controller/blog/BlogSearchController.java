package com.regex.blogsearch.adapter.controller.blog;

import com.regex.blogsearch.application.usecase.blog.BlogSearchService;
import com.regex.blogsearch.types.BlogSortType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "${apiPrefix}")
@Validated
public class BlogSearchController {
    private final BlogSearchService blogSearchService;

    @GetMapping("/blogs")
    public String getBlogsByKeyword(
            @Valid @RequestParam(defaultValue = "") @Size(min=1, message = "query field mandatory") final String query,
            @Valid @RequestParam(defaultValue = "1") @Min(value = 1, message = "page is lower than min") @Max(value = 50, message = "page is more than max") Integer page,
            @Valid @RequestParam(defaultValue = "80") @Min(value = 1, message = "size is lower than min") @Max(value = 80, message = "size is more than max") Integer size,
            @Valid @RequestParam(defaultValue = "accuracy") final BlogSortType sort
    ) {
        String blogList = this.blogSearchService.getBlogs(
                page,
                size,
                sort,
                query
        );
        return blogList;
    }
}
