package com.example.demo.services;

import com.example.demo.dataproviders.clients.FootballApiClient;
import com.example.demo.dataproviders.responses.Country;
import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.responses.LeagueDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CountryEnricher implements LeagueDetailsEnricher {
    private final FootballApiClient footballApiClient;

    @Override
    public void enrich(LeagueDetails leagueDetails) {
        List<Country> countries = footballApiClient.fetchCountries();
        Country country = countries.stream()
                .filter(each -> each.getCountryName().equalsIgnoreCase(leagueDetails.getCountryName()))
                .findFirst()
                .orElseThrow(() -> new CountryNotFoundException("Invalid Country Name"));
        leagueDetails.setCountryId(country.getCountryId());
    }
}
