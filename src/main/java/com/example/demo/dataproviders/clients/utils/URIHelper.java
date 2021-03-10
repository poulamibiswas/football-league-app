package com.example.demo.dataproviders.clients.utils;

import com.example.demo.exceptions.ThirdPartyException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.example.demo.dataproviders.clients.utils.ApiUtil.isSuccessful;

@Service
public class URIHelper {
    private final RestTemplate restTemplate;

    public URIHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T doGet(String urlTemplate, Class<T> type, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET,
                request, type, uriVariables);
        if (isSuccessful(responseEntity)) {
            return responseEntity.getBody();
        } else {
            throw new ThirdPartyException("Third Party is not responding correctly");
        }
    }
}
