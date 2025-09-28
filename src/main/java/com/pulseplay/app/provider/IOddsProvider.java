package com.pulseplay.app.provider;

import java.util.List;
import java.util.Map;

import com.pulseplay.app.model.Fixture;

public interface IOddsProvider {
    Map<String, Object> getOdds(String matchId);

    List<Fixture> getFixtures(String date);

    String getVendorName();
}
