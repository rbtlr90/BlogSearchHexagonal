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
public class NaverAPIResponseBody {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<?> items;

    public static NaverAPIResponseBody fromResponseEntity(
            ResponseEntity<String> naverAPIResponseBody,
            ObjectMapper objectMapper) {
        try{
            return objectMapper.readValue(naverAPIResponseBody.getBody(), NaverAPIResponseBody.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
