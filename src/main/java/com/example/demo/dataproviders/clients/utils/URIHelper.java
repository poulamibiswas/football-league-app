package com.example.demo.dataproviders.clients.utils;

import com.example.demo.exceptions.ThirdPartyException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

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
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET,
                    request, type, uriVariables);
            return extractBody(responseEntity);
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            throw new ThirdPartyException("Third Party is not responding correctly" + exception.getLocalizedMessage());
        }
    }

    private <T> T extractBody(ResponseEntity<T> responseEntity) {
        if (responseEntity.getBody() != null)
            return responseEntity.getBody();

        throw new ThirdPartyException("Response Entity is Null");
    }
}
