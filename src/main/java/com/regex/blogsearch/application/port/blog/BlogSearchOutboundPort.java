package com.regex.blogsearch.application.port.blog;

import com.regex.blogsearch.types.BlogSortType;

public interface BlogSearchOutboundPort {
    public String getBlogs(int page, int size, BlogSortType sort, String query);
}
