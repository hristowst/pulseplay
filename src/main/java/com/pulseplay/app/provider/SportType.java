package com.pulseplay.app.provider;

public enum SportType {
    FOOTBALL;

    public static SportType fromString(String sport) {
        return SportType.valueOf(sport.toUpperCase());
    }
}
