package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.League;
import com.example.demo.exceptions.LeagueNotFoundException;
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
class LeagueEnricherTest {
    @InjectMocks
    private LeagueEnricher leagueEnricher;
    @Mock
    private FootballApiClient footballApiClient;

    @Test
    void shouldEnrichWithLeagueId() {
        League league = League.builder().leagueId(10).leagueName("league").build();
        LeagueDetails leagueDetails = LeagueDetails.builder().leagueName("league").countryId(1)
                .countryName("country").build();
        when(footballApiClient.fetchLeaguesForCountry(1)).thenReturn(List.of(league));

        leagueEnricher.enrich(leagueDetails);

        assertEquals(10, leagueDetails.getLeagueId());
    }

    @Test
    void shouldThrowExceptionIncaseNotAValidLeague() {
        League league = League.builder().leagueId(10).leagueName("invalid-league").build();
        LeagueDetails leagueDetails = LeagueDetails.builder().leagueName("league").countryId(1)
                .countryName("country").build();
        when(footballApiClient.fetchLeaguesForCountry(1)).thenReturn(List.of(league));


        assertThrows(LeagueNotFoundException.class, () -> leagueEnricher.enrich(leagueDetails));
    }

}