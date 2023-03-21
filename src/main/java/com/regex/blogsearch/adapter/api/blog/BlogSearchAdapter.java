package com.regex.blogsearch.adapter.api.blog;


import com.regex.blogsearch.application.port.blog.BlogSearchOutboundPort;
import com.regex.blogsearch.types.BlogSortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogSearchAdapter implements BlogSearchOutboundPort {
    private final RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;
    @Value("${kakaoRestAPIUrl}")
    private String kakaoRestAPIUrl;
    @Value("${kakaoRestAPIKey}")
    private String kakaoRestAPIKey;
    @Value("${naverRestAPIUrl}")
    private String naverRestAPIUrl;
    @Value("${naverClientId}")
    private String naverClientId;
    @Value("${naverClientSecret}")
    private String naverClientSecret;


    public String getBlogs(int page, int size, BlogSortType sort, String query) {
        try {
            this.httpHeaders.set("Authorization", String.format("KakaoAK %s", this.kakaoRestAPIKey));

            HttpEntity request = new HttpEntity(this.httpHeaders);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(this.kakaoRestAPIUrl)
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .queryParam("sort", sort)
                    .queryParam("query", query);

            ResponseEntity<String> kakaoBlogSearchResponse = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    request,
                    String.class
            );
            return kakaoBlogSearchResponse.getBody();
        } catch (RestClientException kakaoRestException) {
            try {
                log.error(kakaoRestException.getMessage());
                ResponseEntity<String> naverBlogSearchResponse =
                        this.getNaverBlogApiResponse(page, size, sort, query);
                return naverBlogSearchResponse.getBody();
            } catch (RestClientException naverRestException) {
                log.error(naverRestException.getMessage());
                HttpStatus statusCode = null;
                if (naverRestException instanceof HttpClientErrorException) {
                    HttpClientErrorException httpException = (HttpClientErrorException) naverRestException;
                    statusCode = (HttpStatus) httpException.getStatusCode();
                    log.error(statusCode.toString());
                } else if (naverRestException instanceof HttpClientErrorException) {
                    HttpServerErrorException httpException = (HttpServerErrorException) naverRestException;
                    statusCode = (HttpStatus)  httpException.getStatusCode();
                }
                assert statusCode != null;
                throw new HttpClientErrorException(statusCode, naverRestException.getMessage());
            }
        }
    }

    private ResponseEntity<String> getNaverBlogApiResponse(int page, int size, BlogSortType sort, String query) {
        this.httpHeaders.set("X-Naver-Client-Id", this.naverClientId);
        this.httpHeaders.set("X-Naver-Client-Secret", this.naverClientSecret);

        String naverSortType;
        if (sort.toString() == "recency") {
            naverSortType = "date";
        }
        else {
            naverSortType = "sim";
        }

        HttpEntity request = new HttpEntity(this.httpHeaders);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(this.naverRestAPIUrl)
                .queryParam("start", (page -1) * size + 1)
                .queryParam("display", size)
                .queryParam("sort", naverSortType)
                .queryParam("query", query);

        ResponseEntity<String> naverBlogSearchResponse = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                String.class
        );
        return naverBlogSearchResponse;
    }
}
