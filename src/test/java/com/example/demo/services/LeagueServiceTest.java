package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.Country;
import com.example.demo.dataproviders.responses.League;
import com.example.demo.dataproviders.responses.Standing;
import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.exceptions.LeagueNotFoundException;
import com.example.demo.exceptions.NoLeaderBoardException;
import com.example.demo.responses.LeagueDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueServiceTest {
    @InjectMocks
    private LeagueService leagueService;
    @Mock
    private FootballApiClient footballApiClient;

    @Test
    void shouldGetDetailsForGivenLeagueTeamCountry() {
        String country = "india";
        String league = "EPL";
        String team = "team";
        Country india = Country.builder().countryId(1).countryName("India").build();
        Country australia = Country.builder().countryId(2).countryName("Australia").build();
        List<Country> countries = List.of(india, australia);
        Standing standing = Standing.builder().leagueId(1).leagueName("EPL")
                .teamName("team").countryName("India").overallLeaguePosition(1).build();
        when(footballApiClient.fetchCountries()).thenReturn(countries);
        when(footballApiClient.fetchLeaguesForCountry(1))
                .thenReturn(List.of(League.builder().leagueId(1).leagueName("EPL").build()));
        when(footballApiClient.fetchStandingForLeague(1)).thenReturn(List.of(standing));

        LeagueDetails detailsForLeagueStanding = leagueService.getDetailsForLeagueStanding(country, league, team);

        assertNotNull(detailsForLeagueStanding);
        assertEquals(detailsForLeagueStanding.getLeagueId(), 1);
        assertEquals(detailsForLeagueStanding.getLeagueName(), "EPL");
        assertEquals(detailsForLeagueStanding.getTeamName(), "team");
        assertEquals(detailsForLeagueStanding.getCountryName(), "India");
        assertEquals(detailsForLeagueStanding.getOverallLeaguePosition(), 1);
    }
    @Test
    void shouldThrowExceptionIfTeamNameIsBlank() {
        String country = "india";
        String league = "EPL";
        Country india = Country.builder().countryId(1).countryName("India").build();
        Country australia = Country.builder().countryId(2).countryName("Australia").build();
        List<Country> countries = List.of(india, australia);
        Standing standing = Standing.builder().leagueId(1).leagueName("EPL")
                .teamName("team").countryName("India").overallLeaguePosition(1).build();
        when(footballApiClient.fetchCountries()).thenReturn(countries);
        when(footballApiClient.fetchLeaguesForCountry(1))
                .thenReturn(List.of(League.builder().leagueId(1).leagueName("EPL").build()));
        when(footballApiClient.fetchStandingForLeague(1)).thenReturn(List.of(standing));

        assertThrows(NoLeaderBoardException.class, () ->
                leagueService.getDetailsForLeagueStanding(country, league, "  "));
    }

    @Test
    void shouldThrowNExceptionForMismatchedTeam() {
        String country = "india";
        String league = "EPL";
        String team = "mismatched-team";
        Country india = Country.builder().countryId(1).countryName("India").build();
        Country australia = Country.builder().countryId(2).countryName("Australia").build();
        List<Country> countries = List.of(india, australia);
        Standing standing = Standing.builder().leagueId(1).leagueName("EPL")
                .teamName("team").countryName("India").overallLeaguePosition(1).build();
        when(footballApiClient.fetchCountries()).thenReturn(countries);
        when(footballApiClient.fetchLeaguesForCountry(1))
                .thenReturn(List.of(League.builder().leagueId(1).leagueName("EPL").build()));
        ;
        when(footballApiClient.fetchStandingForLeague(1)).thenReturn(List.of(standing));

        assertThrows(NoLeaderBoardException.class, () -> leagueService.getDetailsForLeagueStanding(country, league, team));
    }

    @Test
    void shouldThrowCountryNotFoundIncaseofInvalidCountry() {
        String country = "usa";
        String league = "EPL";
        String team = "team";
        Country india = Country.builder().countryId(1).countryName("India").build();
        Country australia = Country.builder().countryId(2).countryName("Australia").build();
        List<Country> countries = List.of(india, australia);
        when(footballApiClient.fetchCountries()).thenReturn(countries);

        assertThrows(CountryNotFoundException.class, () -> leagueService
                .getDetailsForLeagueStanding(country, league, team));
        verifyNoMoreInteractions(footballApiClient);
    }

    @Test
    void shouldThrowLeagueNotFoundIncaseofInvalidLeague() {
        String country = "India";
        String league = "InvalidLeague";
        String team = "team";
        Country india = Country.builder().countryId(1).countryName("India").build();
        Country australia = Country.builder().countryId(2).countryName("Australia").build();
        List<Country> countries = List.of(india, australia);
        when(footballApiClient.fetchCountries()).thenReturn(countries);
        when(footballApiClient.fetchLeaguesForCountry(1))
                .thenReturn(List.of(League.builder().leagueId(1).leagueName("EPL").build()));

        assertThrows(LeagueNotFoundException.class, () -> leagueService
                .getDetailsForLeagueStanding(country, league, team));
        verifyNoMoreInteractions(footballApiClient);
    }

    @Test
    void shouldThrowNoLeaderBoardExceptionWhenNoDetailsAvailable() {
        String country = "india";
        String league = "EPL";
        String team = "team";
        Country india = Country.builder().countryId(1).countryName("India").build();
        Country australia = Country.builder().countryId(2).countryName("Australia").build();
        List<Country> countries = List.of(india, australia);

        when(footballApiClient.fetchCountries()).thenReturn(countries);
        when(footballApiClient.fetchLeaguesForCountry(1))
                .thenReturn(List.of(League.builder().leagueId(1).leagueName("EPL").build()));
        when(footballApiClient.fetchStandingForLeague(1)).thenReturn(emptyList());

        assertThrows(NoLeaderBoardException.class, () -> leagueService
                .getDetailsForLeagueStanding(country, league, team));
    }

}