package com.regex.blogsearch.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeywordSortOrder {
    DESC("DESC"),
    ASC("ASC");

    private final String sortOrder;
}
