package com.example.demo.dataproviders.clients.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtil {
    public static <T> boolean isSuccessful(ResponseEntity<T> response) {
        return response != null && response.getStatusCode() == HttpStatus.OK && response.hasBody();
    }
}