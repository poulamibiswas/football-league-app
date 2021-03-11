package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.Standing;
import com.example.demo.exceptions.NoLeaderBoardException;
import com.example.demo.responses.LeagueDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StandingEnricher implements LeagueDetailsEnricher {
    private final FootballApiClient footballApiClient;

    @Override
    public void enrich(LeagueDetails leagueDetails) {
        List<Standing> standings = footballApiClient.fetchStandingForLeague(leagueDetails.getLeagueId());
        Standing standing = standings
                .stream()
                .filter(each -> each.getTeamName().equalsIgnoreCase(leagueDetails.getTeamName()))
                .findFirst().orElseThrow(() -> new NoLeaderBoardException("No information available for given team"));
        leagueDetails.setTeamId(standing.getTeamId());
        leagueDetails.setOverallLeaguePosition(standing.getOverallLeaguePosition());
    }
}
