package com.pulseplay.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pulseplay.app.model.Fixture;
import com.pulseplay.app.provider.SportType;
import com.pulseplay.app.service.OddsService;

@RestController
public class SportsController {

    private final OddsService oddsService;

    public SportsController(OddsService oddsService) {
        this.oddsService = oddsService;
    }

    @GetMapping("/sports/{sport}/fixtures")
    public ResponseEntity<List<Fixture>> getFootballFixtures(@PathVariable String sport) {
        return ResponseEntity.ok(oddsService.getFixtures(SportType.fromString(sport)));
    }

    @GetMapping("/sports/{sport}/fixtures/{fixtureId}/odds")
    public ResponseEntity<Map<String, Object>> getOddsBySportAndId(@PathVariable String sport,
            @PathVariable String fixtureId) {
        var result = oddsService.getOdds(SportType.fromString(sport), fixtureId);
        return ResponseEntity.ok(result);
    }

}
