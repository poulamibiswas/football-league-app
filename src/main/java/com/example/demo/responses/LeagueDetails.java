package com.example.demo.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueDetails {
    private int countryId;
    private String countryName;
    private int leagueId;
    private String leagueName;
    private int teamId;
    private String teamName;
    private int overallLeaguePosition;
}
