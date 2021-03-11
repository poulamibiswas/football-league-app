package com.example.demo.controllers;

import com.example.demo.responses.LeagueDetails;
import com.example.demo.services.LeagueService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/football")
@Validated
@AllArgsConstructor
public class LeagueController {
    private final LeagueService leagueService;

    @GetMapping("/standings")
    public ResponseEntity<LeagueDetails> fetchStandingForTeam(@RequestParam @NonNull final String country,
                                                              @RequestParam @NonNull final String league,
                                                              @RequestParam @NonNull final String team) {
        return ResponseEntity.ok()
                .body(leagueService.getDetailsForLeagueStanding(country, league, team));
    }
}
