package com.example.demo.services;

import com.example.demo.responses.LeagueDetails;

@FunctionalInterface
public interface LeagueDetailsEnricher {
    default LeagueDetailsEnricher setNextEnricher(LeagueDetailsEnricher nextChain) {
        return (leagueDetails) -> {
            this.enrich(leagueDetails);
            nextChain.enrich(leagueDetails);
        };
    }

    void enrich(LeagueDetails leagueDetails);
}
