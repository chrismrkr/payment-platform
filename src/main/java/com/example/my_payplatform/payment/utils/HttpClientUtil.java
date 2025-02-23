package com.example.my_payplatform.payment.utils;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface HttpClientUtil {
    <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, String> headers, MediaType mediaType);
    <T, R> ResponseEntity<T> post(String url, Class<T> responseType, R requestBody, Map<String, String> headers, MediaType mediaType);
}
