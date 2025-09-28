package com.pulseplay.app.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pulseplay.app.model.Fixture;
import com.pulseplay.app.provider.IOddsProvider;
import com.pulseplay.app.provider.OddsProviderFactory;
import com.pulseplay.app.provider.SportType;

@Service
public class OddsService {

    OddsProviderFactory oddsProviderFactory;

    public OddsService(OddsProviderFactory oddsProviderFactory) {
        this.oddsProviderFactory = oddsProviderFactory;
    }

    public List<Fixture> getFixtures(SportType sport) {
        var provider = oddsProviderFactory.get(sport);
        return getTwoDayFixtures(provider);
    }

    public Map<String, Object> getOdds(SportType sport, String fixtureId) {
        var provider = oddsProviderFactory.get(sport);
        return provider.getOdds(fixtureId);
    }

    private List<Fixture> getTwoDayFixtures(IOddsProvider oddsProvider) {
        String day1 = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String day2 = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        List<Fixture> result = new ArrayList<>();
        result.addAll(oddsProvider.getFixtures(day1));
        result.addAll(oddsProvider.getFixtures(day2));
        return result;
    }
}
