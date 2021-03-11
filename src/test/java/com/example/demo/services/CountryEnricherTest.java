package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.Country;
import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.responses.LeagueDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryEnricherTest {
    @InjectMocks
    private CountryEnricher countryEnricher;
    @Mock
    private FootballApiClient footballApiClient;

    @Test
    void shouldEnrichCountryInLeagueDetails() {
        Country australia = Country.builder().countryId(2).countryName("country").build();
        List<Country> countries = List.of(australia);
        when(footballApiClient.fetchCountries()).thenReturn(countries);
        LeagueDetails leagueDetails = LeagueDetails.builder().countryName("country").build();

        countryEnricher.enrich(leagueDetails);

        assertEquals(2, leagueDetails.getCountryId());
    }

    @Test
    void shouldThrowExceptionIncaseNotAValidCountry() {
        Country australia = Country.builder().countryId(2).countryName("invalidCountry").build();
        List<Country> countries = List.of(australia);
        when(footballApiClient.fetchCountries()).thenReturn(countries);
        LeagueDetails leagueDetails = LeagueDetails.builder().countryName("country").build();

        assertThrows(CountryNotFoundException.class, () -> countryEnricher.enrich(leagueDetails));
    }
}