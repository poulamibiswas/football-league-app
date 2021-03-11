package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.League;
import com.example.demo.exceptions.LeagueNotFoundException;
import com.example.demo.responses.LeagueDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LeagueEnricher implements LeagueDetailsEnricher {
    private final FootballApiClient footballApiClient;

    @Override
    public void enrich(LeagueDetails leagueDetails) {
        List<League> leagues = footballApiClient.fetchLeaguesForCountry(leagueDetails.getCountryId());
        League league = leagues.stream()
                .filter(each -> each.getLeagueName().equalsIgnoreCase(leagueDetails.getLeagueName()))
                .findFirst()
                .orElseThrow(() -> new LeagueNotFoundException("Invalid League"));
        leagueDetails.setLeagueId(league.getLeagueId());
    }
}
