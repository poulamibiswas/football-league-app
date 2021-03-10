package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.Country;
import com.example.demo.dataproviders.responses.League;
import com.example.demo.dataproviders.responses.Standing;
import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.exceptions.LeagueNotFoundException;
import com.example.demo.exceptions.NoLeaderBoardException;
import com.example.demo.responses.LeagueDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueService {
    private final FootballApiClient footballApiClient;

    public LeagueDetails getDetailsForLeagueStanding(String country, String league, String team) {
        log.info("Fetching details for country {} league {} team {}", country, league, team);
        Country selectedCountry = getSelectedCountry(country);
        League selectedLeague = getLeague(league, selectedCountry);

        List<Standing> standings = filterStandingForGivenTeam(team, selectedLeague);
        LeagueDetails leagueDetails = standings.stream()
                .map(standing -> mapFrom(selectedCountry, standing))
                .findFirst()
                .orElseThrow(() -> new NoLeaderBoardException("No leader board information available"));
        return leagueDetails;
    }

    private List<Standing> filterStandingForGivenTeam(String team, League selectedLeague) {
        List<Standing> standings = footballApiClient.fetchStandingForLeague(selectedLeague.getLeagueId());
        if (Objects.isNull(team)) {
            return standings;
        } else {
            return standings
                    .stream()
                    .filter(standing -> standing.getTeamName().equalsIgnoreCase(team))
                    .collect(Collectors.toList());
        }
    }

    private LeagueDetails mapFrom(Country selectedCountry, Standing standing) {
        return LeagueDetails.builder()
                .leagueId(standing.getLeagueId())
                .leagueName(standing.getLeagueName())
                .countryId(selectedCountry.getCountryId())
                .countryName(selectedCountry.getCountryName())
                .teamId(standing.getTeamId())
                .teamName(standing.getTeamName())
                .overallLeaguePosition(standing.getOverallLeaguePosition()).build();
    }

    private League getLeague(String league, Country selectedCountry) {
        List<League> leagues = footballApiClient.fetchLeaguesForCountry(selectedCountry.getCountryId());
        return leagues.stream()
                .filter(each -> each.getLeagueName().equalsIgnoreCase(league))
                .findFirst()
                .orElseThrow(() -> new LeagueNotFoundException("Invalid League"));
    }

    private Country getSelectedCountry(String country) {
        List<Country> countries = footballApiClient.fetchCountries();
        return countries.stream()
                .filter(each -> each.getCountryName().equalsIgnoreCase(country))
                .findFirst()
                .orElseThrow(() -> new CountryNotFoundException("Invalid Country Name"));
    }
}
