package com.pulseplay.app.provider;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class OddsProviderFactory {
    private final Map<SportType, IOddsProvider> registry = new HashMap<>();

    public void register(SportType type, IOddsProvider oddsProvider) {
        registry.put(type, oddsProvider);
    }

    public IOddsProvider get(SportType type) {
        IOddsProvider oddsProvider = registry.get(type);
        if (oddsProvider == null) {
            throw new IllegalArgumentException("No provider registered for: " + type);
        }
        return oddsProvider;
    }
}
