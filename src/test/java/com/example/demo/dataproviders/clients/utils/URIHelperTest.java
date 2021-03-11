package com.example.demo.dataproviders.clients.utils;

import com.example.demo.config.RestTemplateConfig;
import com.example.demo.dataproviders.responses.Country;
import com.example.demo.exceptions.ThirdPartyException;
import com.example.demo.testutils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ContextConfiguration(classes = URIHelper.class)
@RunWith(SpringRunner.class)
@Import({RestTemplateConfig.class})
@RestClientTest(URIHelper.class)
class URIHelperTest {
    @Autowired
    URIHelper uriHelper;
    @Autowired
    private RestTemplate restTemplate;

    MockRestServiceServer server;


    @BeforeEach
    void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldCompleteGetCallSuccessfully() {
        String url = "http://foo";
        server.expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(method(HttpMethod.GET)).andRespond(withSuccess(
                TestUtil.dataFromResources("countries.json"), MediaType.APPLICATION_JSON));

        Country[] countries = uriHelper.doGet(url, Country[].class, "apiKey");

        assertEquals(3, countries.length);
        assertEquals(41, countries[0].getCountryId());
        assertEquals(68, countries[1].getCountryId());
        assertEquals(135, countries[2].getCountryId());
    }

    @Test
    void shouldThrowExceptionIfNon200IsReturned() {
        String url = "http://foo";
        server.expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        assertThrows(ThirdPartyException.class, () -> uriHelper.doGet(url, Country[].class, "apiKey"));
    }

    @Test
    void shouldThrowExceptionIfEmptyBodyIsReturned() {
        String url = "http://foo";
        server.expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(method(HttpMethod.GET)).andRespond(withSuccess());
        assertThrows(ThirdPartyException.class, () -> uriHelper.doGet(url, Country[].class, "apiKey"));
    }
}