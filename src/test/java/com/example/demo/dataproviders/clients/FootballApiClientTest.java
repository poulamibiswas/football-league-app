package com.example.demo.dataproviders.clients;

import com.example.demo.dataproviders.clients.utils.URIHelper;
import com.example.demo.dataproviders.responses.Country;
import com.example.demo.dataproviders.responses.League;
import com.example.demo.dataproviders.responses.Standing;
import com.example.demo.exceptions.ThirdPartyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FootballApiClientTest {
    @InjectMocks
    private FootballApiClient client;
    @Mock
    private URIHelper uriHelper;

    @Test
    void shouldGetAllCountriesSuccessfully() {
        ReflectionTestUtils.setField(client, "baseUrl", "http://foo/");

        String url = "http://foo/?action=get_countries&APIkey={apikey}";
        Country mock = mock(Country.class);
        Country[] countries = new Country[]{mock};
        when(uriHelper.doGet(eq(url), eq(Country[].class), any())).thenReturn(countries);

        List<Country> response = client.fetchCountries();
        assertEquals(response.get(0), mock);
    }

    @Test
    void shouldGetAllLeaguesForGivenCountry() {
        ReflectionTestUtils.setField(client, "baseUrl", "http://foo/");

        String url = "http://foo/?action=get_leagues&country_id={countryId}&APIkey={apikey}";
        League mock = mock(League.class);
        League[] leagues = new League[]{mock};
        when(uriHelper.doGet(eq(url), eq(League[].class), eq(1), any())).thenReturn(leagues);

        List<League> response = client.fetchLeaguesForCountry(1);
        assertEquals(response.get(0), mock);
    }

    @Test
    void shouldGetAllStandingForGivenLeague() {
        ReflectionTestUtils.setField(client, "baseUrl", "http://foo/");

        String url = "http://foo/?action=get_standings&league_id={leagueId}&APIkey={apikey}";
        Standing mock = mock(Standing.class);
        Standing[] standings = new Standing[]{mock};
        when(uriHelper.doGet(eq(url), eq(Standing[].class), eq(1), any())).thenReturn(standings);

        List<Standing> response = client.fetchStandingForLeague(1);
        assertEquals(response.get(0), mock);
    }

    @Test
    void shouldThrowExceptionWhenThirdpartyFails() {
        ReflectionTestUtils.setField(client, "baseUrl", "http://foo/");

        String url = "http://foo/?action=get_standings&league_id={leagueId}&APIkey={apikey}";
        Standing mock = mock(Standing.class);
        when(uriHelper.doGet(eq(url), eq(Standing[].class), eq(1), any())).thenThrow(ThirdPartyException.class);

        assertThrows(ThirdPartyException.class, () -> client.fetchStandingForLeague(1));
    }
}