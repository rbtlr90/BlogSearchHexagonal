package com.regex.blogsearch.types;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NaverAPIResponseBody {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<String> items;
}
