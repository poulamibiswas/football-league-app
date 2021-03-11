package com.example.demo.controllers;

import com.example.demo.responses.LeagueDetails;
import com.example.demo.services.LeagueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.testutils.TestUtil.dataFromResources;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LeagueController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LeagueControllerTest {
    @MockBean
    private LeagueService leagueService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetLeaderBoardDetailsWhenAllQueryParamsArePresent() throws Exception {
        String countryName = "country";
        String leagueName = "league";
        String teamName = "team";
        LeagueDetails mock = LeagueDetails.builder()
                .leagueId(1).leagueName("some name").countryId(122).countryName("India")
                .build();
        when(leagueService.getDetailsForLeagueStanding(countryName, leagueName, teamName))
                .thenReturn(mock);
        mockMvc.perform(
                get("/football/standings?country={countryName}&league={leagueName}&team={teamName}",
                        countryName, leagueName, teamName)
        ).andExpect(status().isOk())
                .andExpect(content().json(dataFromResources("leaderboarddetails.json")));

        verify(leagueService).getDetailsForLeagueStanding(countryName, leagueName, teamName);
    }

    @Test
    void shouldReturnBadRequestTeamNameIsNotPresent() throws Exception {
        String countryName = "country";
        String leagueName = "league";
        LeagueDetails mock = LeagueDetails.builder()
                .leagueId(1).leagueName("some name").countryId(122).countryName("India")
                .build();
        when(leagueService.getDetailsForLeagueStanding(countryName, leagueName, null))
                .thenReturn(mock);
        mockMvc.perform(
                get("/football/standings?country={countryName}&league={leagueName}",
                        countryName, leagueName)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(leagueService);
    }

    @Test
    void shouldReturn400WhenMandatoryParamsAreNotPresent() throws Exception {
        String countryName = "country";
        LeagueDetails mock = LeagueDetails.builder()
                .leagueId(1).leagueName("some name").countryId(122).countryName("India")
                .build();
        when(leagueService.getDetailsForLeagueStanding(countryName, null, null))
                .thenReturn(mock);
        mockMvc.perform(
                get("/football/standings?country={countryName}",
                        countryName)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(leagueService);
    }
}