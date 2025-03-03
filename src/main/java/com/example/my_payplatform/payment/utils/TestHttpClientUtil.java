package com.example.my_payplatform.payment.utils;

import com.example.my_payplatform.payment.service.executor.popularPSP.PopularPSPPaymentEnrollResDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class TestHttpClientUtil implements HttpClientUtil{
    @Override
    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, String> headers, MediaType mediaType) {
        return null;
    }

    @Override
    public <T, R> ResponseEntity<T> post(String url, Class<T> responseType, R requestBody, Map<String, String> headers, MediaType mediaType) {
        PopularPSPPaymentEnrollResDto popularPSPPaymentEnrollResDto =
                new PopularPSPPaymentEnrollResDto("0", "12345paymenttoken54321");
        ResponseEntity<PopularPSPPaymentEnrollResDto> popularPSPPaymentEnrollResDtoResponseEntity =
                new ResponseEntity<>(popularPSPPaymentEnrollResDto, HttpStatusCode.valueOf(200));
        return (ResponseEntity<T>) popularPSPPaymentEnrollResDtoResponseEntity;
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
