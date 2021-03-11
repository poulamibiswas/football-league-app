package com.example.demo.services;

import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.responses.LeagueDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeagueServiceTest {
    @InjectMocks
    private LeagueService leagueService;
    @Mock
    private LeagueEnricherProvider provider;

    @Test
    void shouldInvokeEnrich() {
        LeagueDetails leagueDetails = LeagueDetails.builder().teamName("team").leagueName("league").countryName("country").build();
        doNothing().when(provider).enrich(leagueDetails);

        leagueService.getDetailsForLeagueStanding("country", "league", "team");

        verify(provider).enrich(leagueDetails);
    }

    @Test
    void shouldThrowExceptionIfEnrichThrowsException() {
        LeagueDetails leagueDetails = LeagueDetails.builder().teamName("team").leagueName("league").countryName("country").build();
        doThrow(CountryNotFoundException.class).when(provider).enrich(leagueDetails);

        assertThrows(CountryNotFoundException.class,
                () -> leagueService.getDetailsForLeagueStanding("country", "league", "team"));
    }
}