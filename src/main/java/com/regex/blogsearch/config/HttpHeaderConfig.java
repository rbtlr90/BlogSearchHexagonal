package com.regex.blogsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class HttpHeaderConfig {
    @Bean(name="httpHeader")
    public HttpHeaders httpHeaders() {
        return new HttpHeaders();
    }
}
