package com.regex.blogsearch.application.port.blog;

import com.regex.blogsearch.types.BlogSortType;

public interface BlogSearchUsecase {
    String getBlogs(int page, int size, BlogSortType sort, String query);
}
