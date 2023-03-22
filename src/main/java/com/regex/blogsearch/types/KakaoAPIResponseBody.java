package com.regex.blogsearch.types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class KakaoAPIResponseBody {
    private KakaoAPIResponseMeta meta;
    private List<?> documents;

    public static KakaoAPIResponseBody fromResponseEntity(
            ResponseEntity<String> kakaoAPIResponseBody,
            ObjectMapper objectMapper) {
        try{
            return objectMapper.readValue(kakaoAPIResponseBody.getBody(), KakaoAPIResponseBody.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
