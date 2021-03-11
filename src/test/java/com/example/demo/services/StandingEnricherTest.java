package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.Standing;
import com.example.demo.exceptions.NoLeaderBoardException;
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
class StandingEnricherTest {
    @InjectMocks
    private StandingEnricher standingEnricher;
    @Mock
    private FootballApiClient footballApiClient;
    @Test
    void shouldEnrichWithStanding() {
        Standing standing = Standing.builder().leagueId(1).leagueName("league").teamId(100)
                .teamName("team").countryName("country").overallLeaguePosition(1).build();
        LeagueDetails leagueDetails = LeagueDetails.builder().leagueName("league").teamName("team").leagueId(1).countryId(1)
                .countryName("country").build();
        when(footballApiClient.fetchStandingForLeague(1)).thenReturn(List.of(standing));

        standingEnricher.enrich(leagueDetails);

        assertEquals(100, leagueDetails.getTeamId());
        assertEquals(1, leagueDetails.getOverallLeaguePosition());
    }

    @Test
    void shouldThrowExceptionIncaseNotAValidTeam() {
        Standing standing = Standing.builder().leagueId(1).leagueName("league").teamId(100)
                .teamName("team").countryName("country").overallLeaguePosition(1).build();
        LeagueDetails leagueDetails = LeagueDetails.builder().leagueName("league").leagueId(1).countryId(1)
                .countryName("country").teamName("invalid").build();
        when(footballApiClient.fetchStandingForLeague(1)).thenReturn(List.of(standing));


        assertThrows(NoLeaderBoardException.class, () -> standingEnricher.enrich(leagueDetails));
    }
}