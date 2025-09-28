package com.pulseplay.app.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OddsDto {
    private String matchId;
    private double homeWinOdds;
    private double drawOdds;
    private double awayWinOdds;
    private LocalDateTime lastUpdated;
}