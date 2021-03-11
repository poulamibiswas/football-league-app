package com.example.demo.services;

import com.example.demo.responses.LeagueDetails;
import org.springframework.stereotype.Component;

@Component
public class LeagueEnricherProvider implements LeagueDetailsEnricher {

    private final LeagueDetailsEnricher leagueDetailsEnricher;

    public LeagueEnricherProvider(CountryEnricher countryEnricher,
                                  LeagueEnricher leagueEnricher,
                                  StandingEnricher standingEnricher) {
        this.leagueDetailsEnricher = countryEnricher.setNextEnricher(leagueEnricher).setNextEnricher(standingEnricher);
    }

    @Override
    public void enrich(LeagueDetails leagueDetails) {
        leagueDetailsEnricher.enrich(leagueDetails);
    }
}
