package com.pulseplay.app.dto;

public record LeagueDTO(
        Long id,
        String name,
        String country,
        String logoUrl,
        String flagUrl) {
}
