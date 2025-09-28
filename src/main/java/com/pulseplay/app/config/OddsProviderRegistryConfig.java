package com.pulseplay.app.config;

import org.springframework.context.annotation.Configuration;

import com.pulseplay.app.provider.OddsProviderFactory;
import com.pulseplay.app.provider.SportType;
import com.pulseplay.app.provider.football.FootballOddsProvider;

import jakarta.annotation.PostConstruct;

@Configuration
public class OddsProviderRegistryConfig {

    private final OddsProviderFactory factory;
    private final FootballOddsProvider footballProvider;

    public OddsProviderRegistryConfig(OddsProviderFactory factory,
            FootballOddsProvider footballProvider) {
        this.factory = factory;
        this.footballProvider = footballProvider;
    }

    @PostConstruct
    public void init() {
        factory.register(SportType.FOOTBALL, footballProvider);
    }
}
