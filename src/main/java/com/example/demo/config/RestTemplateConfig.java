package com.example.demo.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.List;

@Configuration
public class RestTemplateConfig {

//    @Bean
//    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(
//            @Value("${externalServices.httpPoolSize}") int httpPoolSize) {
//        PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
//        result.setMaxTotal(httpPoolSize);
//        return result;
//    }
//
//    @Bean
//    public CloseableHttpClient httpClient(
//            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
//        Header header = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
//        return HttpClientBuilder
//                .create()
//                .setDefaultHeaders(List.of(header))
//                .setConnectionManager(poolingHttpClientConnectionManager)
//                .build();
//    }
//
//    @Bean
//    public RestTemplate restTemplate(HttpClient httpClient, RestTemplateBuilder restTemplateBuilder) {
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        return restTemplateBuilder.requestFactory(() -> requestFactory).build();
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
