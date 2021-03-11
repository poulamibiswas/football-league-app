package com.example.demo.services;

import com.example.demo.responses.LeagueDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueService {
    private final LeagueEnricherProvider enricher;

    public LeagueDetails getDetailsForLeagueStanding(String country, String league, String team) {
        log.info("Fetching details for country {} league {} team {}", country, league, team);
        LeagueDetails leagueDetails = LeagueDetails.builder().countryName(country)
                .leagueName(league).teamName(team).build();
        enricher.enrich(leagueDetails);
        return leagueDetails;
    }
}
