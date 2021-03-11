package com.example.demo.dataproviders.clients;

import com.example.demo.dataproviders.clients.utils.URIHelper;
import com.example.demo.dataproviders.responses.Country;
import com.example.demo.dataproviders.responses.League;
import com.example.demo.dataproviders.responses.Standing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class FootballApiClient {
    private final URIHelper uriHelper;
    private final String baseUrl;
    private final String apikey = "<insert-api-key>";

    public static final String GET_COUNTRIES_URL_TEMPLATE = "?action=get_countries&APIkey={apikey}";
    public static final String GET_LEAGUES_URL_TEMPLATE = "?action=get_leagues&country_id={countryId}&APIkey={apikey}";
    public static final String GET_STANDING_URL_TEMPLATE = "?action=get_standings&league_id={leagueId}&APIkey={apikey}";

    public FootballApiClient(URIHelper uriHelper,
                             @Value("${footballapi.base.url}") String baseUrl) {
        this.uriHelper = uriHelper;
        this.baseUrl = baseUrl;
    }

    public List<Country> fetchCountries() {
        Country[] countries = uriHelper.doGet(getGetCountriesUrlTemplate(), Country[].class, apikey);
        return List.of(countries);
    }

    public List<League> fetchLeaguesForCountry(int countryId) {
        League[] leagues = uriHelper.doGet(getGetLeaguesUrlTemplate(), League[].class, countryId, apikey);
        return List.of(leagues);
    }

    public List<Standing> fetchStandingForLeague(int leagueId) {
        Standing[] standings = uriHelper.doGet(getGetStandingUrlTemplate(), Standing[].class, leagueId, apikey);
        return List.of(standings);
    }

    private String getGetCountriesUrlTemplate() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .toUriString();
        return url + GET_COUNTRIES_URL_TEMPLATE;
    }

    private String getGetLeaguesUrlTemplate() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .toUriString();
        return url + GET_LEAGUES_URL_TEMPLATE;
    }

    private String getGetStandingUrlTemplate() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .toUriString();
        return url + GET_STANDING_URL_TEMPLATE;
    }
}
