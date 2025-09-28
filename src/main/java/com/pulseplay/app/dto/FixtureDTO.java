package com.pulseplay.app.dto;

public record FixtureDTO(
        Long id,
        String date,
        String status,
        VenueDTO venue,
        LeagueDTO league,
        TeamDTO homeTeam,
        TeamDTO awayTeam,
        ScoreDTO score) {
}