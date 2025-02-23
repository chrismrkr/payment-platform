package com.example.my_payplatform.payment.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SyncHttpClientUtil implements HttpClientUtil {
    private final RestTemplate restTemplate;
    @Override
    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, String> headers, MediaType mediaType) {
        HttpEntity<String> entity = new HttpEntity<>(buildHeaders(headers, mediaType));
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        } catch (RestClientException e) {
            // 에러 로깅 및 처리
            throw new RuntimeException("FAIL GET HTTP Request" + e.getMessage(), e);
        }
    }

    @Override
    public <T, R> ResponseEntity<T> post(String url, Class<T> responseType, R requestBody, Map<String, String> headers, MediaType mediaType) {
        HttpEntity<R> entity = new HttpEntity<>(requestBody, buildHeaders(headers, mediaType));
        try {
            return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        } catch (RestClientException e) {
            throw new RuntimeException("POST 요청 실패: " + e.getMessage(), e);
        }
    }

    private HttpHeaders buildHeaders(Map<String, String> headers, MediaType mediaType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);
        if(headers != null) {
            headers.forEach(httpHeaders::set);
        }
        return httpHeaders;
    }
}
