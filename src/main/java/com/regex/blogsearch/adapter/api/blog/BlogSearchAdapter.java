package com.regex.blogsearch.adapter.api.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.regex.blogsearch.application.port.blog.BlogSearchOutboundPort;
import com.regex.blogsearch.dto.BlogSearchDTO;
import com.regex.blogsearch.types.BlogSortType;
import com.regex.blogsearch.types.KakaoAPIResponseBody;
import com.regex.blogsearch.types.NaverAPIResponseBody;
import jakarta.validation.ConstraintViolationException;
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
    @Autowired
    private ObjectMapper objectMapper;
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


    public BlogSearchDTO getBlogs(int page, int size, BlogSortType sort, String query) {
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
            KakaoAPIResponseBody kakaoAPIResponseBody = KakaoAPIResponseBody.fromResponseEntity(
                    kakaoBlogSearchResponse,
                    this.objectMapper);
            return BlogSearchDTO.fromKakaoApiResponse(kakaoAPIResponseBody);
        } catch (RestClientException kakaoRestException) {
            try {
                log.error(kakaoRestException.getMessage());

                HttpStatus statusCode = null;
                if (kakaoRestException instanceof HttpClientErrorException) {
                    HttpClientErrorException httpException = (HttpClientErrorException) kakaoRestException;
                    statusCode = (HttpStatus) httpException.getStatusCode();
                } else if (kakaoRestException instanceof HttpClientErrorException) {
                    HttpServerErrorException httpException = (HttpServerErrorException) kakaoRestException;
                    statusCode = (HttpStatus)  httpException.getStatusCode();
                }
                assert statusCode != null;
                if (statusCode == HttpStatus.BAD_REQUEST) {
                    throw new ConstraintViolationException(kakaoRestException.getMessage(), null);
                }

                ResponseEntity<String> naverBlogSearchResponse =
                        this.getNaverBlogApiResponse(page, size, sort, query);
                NaverAPIResponseBody naverAPIResponseBody = NaverAPIResponseBody.fromResponseEntity(
                        naverBlogSearchResponse,
                        this.objectMapper
                );
                return BlogSearchDTO.fromNaverApiResponse(naverAPIResponseBody);
            } catch (RestClientException naverRestException) {
                log.error(naverRestException.getMessage());
                HttpStatus statusCode = null;
                if (naverRestException instanceof HttpClientErrorException) {
                    HttpClientErrorException httpException = (HttpClientErrorException) naverRestException;
                    statusCode = (HttpStatus) httpException.getStatusCode();
                } else if (naverRestException instanceof HttpClientErrorException) {
                    HttpServerErrorException httpException = (HttpServerErrorException) naverRestException;
                    statusCode = (HttpStatus)  httpException.getStatusCode();
                }
                assert statusCode != null;
                if (statusCode == HttpStatus.BAD_REQUEST) {
                    throw new ConstraintViolationException(naverRestException.getMessage(), null);
                }
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
