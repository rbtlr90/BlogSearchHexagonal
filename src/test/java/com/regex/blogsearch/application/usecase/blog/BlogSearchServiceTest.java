package com.regex.blogsearch.application.usecase.blog;

import com.regex.blogsearch.application.port.blog.BlogSearchOutboundPort;
import com.regex.blogsearch.application.port.keyword.KeywordOutboundPort;
import com.regex.blogsearch.domain.keyword.Keyword;
import com.regex.blogsearch.dto.BlogSearchDTO;
import com.regex.blogsearch.types.BlogSortType;
import com.regex.blogsearch.types.KakaoAPIResponseMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogSearchServiceTest {

    @Mock
    private KeywordOutboundPort keywordOutboundPort;
    @Mock
    private BlogSearchOutboundPort blogSearchOutboundPort;

    @InjectMocks
    private BlogSearchService blogSearchService;

    @Test
    void getBlogs() {
        //given
        int page = 1;
        int size = 1;
        BlogSortType sort = BlogSortType.accuracy;
        String query = "kakao";

        KakaoAPIResponseMeta meta = new KakaoAPIResponseMeta(
                false,
                981,
                1116438
        );
        String apiResponseContent = "{\n" +
                "\t\t\t\"contents\": \"Prémontré.[<b>20</b>] Middle Ages[edit] Dominic&#39;s room at Maison Seilhan, in Toulouse, is considered the place where the Order was born. In July 1215, with the approbation of Bishop Foulques of Toulouse...\",\n" +
                "\t\t\t\"datetime\": \"2023-03-18T08:04:28.000+09:00\",\n" +
                "\t\t\t\"title\": \"Dominican Order - Wikipedia\",\n" +
                "\t\t\t\"url\": \"https://www.non-stop.kr/BsTransOriginPage.php?tpv=&tplFileName=cntTpl220419013721-144014537&t_url=http%3A%2F%2Fen.wikipedia.org%2Fwiki%2FDominican_Sisters&isref=1\"\n" +
                "\t\t}";

        given(blogSearchOutboundPort.getBlogs(page, size, sort, query))
                .willReturn(BlogSearchDTO.builder()
                        .meta(meta)
                        .documents(Arrays.asList(apiResponseContent))
                        .build());
        given(keywordOutboundPort.findByKeyword(query))
                .willReturn(Optional.ofNullable(Keyword.builder()
                        .id(1L)
                        .keyword(query)
                        .searchCount(1)
                        .build()));

        //when
        BlogSearchDTO blogSearchDTO = blogSearchService.getBlogs(page, size, sort, query);


        //then
        assertEquals(blogSearchDTO.getMeta(), meta);
        assertEquals(blogSearchDTO.getDocuments(), Arrays.asList(apiResponseContent));
    }

}