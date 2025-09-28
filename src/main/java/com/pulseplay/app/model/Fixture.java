package com.pulseplay.app.model;

import lombok.Data;

@Data
public class Fixture {
    private Long id;
    private String date;
    private String status;
    private Venue venue;
    private League league;
    private Team homeTeam;
    private Team awayTeam;
    private Score score;
}
