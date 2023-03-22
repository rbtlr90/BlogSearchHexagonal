package com.regex.blogsearch.application.port.blog;

import com.regex.blogsearch.dto.BlogSearchDTO;
import com.regex.blogsearch.types.BlogSortType;

public interface BlogSearchUsecase {
    BlogSearchDTO getBlogs(int page, int size, BlogSortType sort, String query);
}
