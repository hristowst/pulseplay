package com.pulseplay.app.provider.football;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pulseplay.app.dto.FixtureDTO;
import com.pulseplay.app.dto.LeagueDTO;
import com.pulseplay.app.dto.ScoreDTO;
import com.pulseplay.app.dto.TeamDTO;
import com.pulseplay.app.dto.VenueDTO;
import com.pulseplay.app.model.Fixture;
import com.pulseplay.app.model.League;
import com.pulseplay.app.model.Score;
import com.pulseplay.app.model.Team;
import com.pulseplay.app.model.Venue;

public class FootballOddsMapper {

    public static FixtureDTO toDto(Fixture fixture) {
        return new FixtureDTO(
                fixture.getId(),
                fixture.getDate(),
                fixture.getStatus(),
                new VenueDTO(fixture.getVenue().getId(), fixture.getVenue().getName(), fixture.getVenue().getCity()),
                new LeagueDTO(fixture.getLeague().getId(), fixture.getLeague().getName(),
                        fixture.getLeague().getCountry(), fixture.getLeague().getLogoUrl(),
                        fixture.getLeague().getFlagUrl()),
                new TeamDTO(fixture.getHomeTeam().getId(), fixture.getHomeTeam().getName(),
                        fixture.getHomeTeam().getLogoUrl(), fixture.getHomeTeam().getWinner()),
                new TeamDTO(fixture.getAwayTeam().getId(), fixture.getAwayTeam().getName(),
                        fixture.getAwayTeam().getLogoUrl(), fixture.getAwayTeam().getWinner()),
                new ScoreDTO(fixture.getScore().getHome(), fixture.getScore().getAway()));
    }

    @SuppressWarnings("unchecked")
    public static List<Fixture> mapApiResponseToFixtures(List<Map<String, Object>> responseList) {
        List<Fixture> fixtures = new ArrayList<>();
        for (Map<String, Object> fixtureMap : responseList) {
            fixtures.add(mapSingleFixture(fixtureMap));
        }
        return fixtures;
    }

    @SuppressWarnings("unchecked")
    private static Fixture mapSingleFixture(Map<String, Object> fixtureMap) {
        Fixture fixture = new Fixture();
        fixture.setLeague(mapLeague((Map<String, Object>) fixtureMap.get("league")));
        fixture.setHomeTeam(mapTeam((Map<String, Object>) ((Map<String, Object>) fixtureMap.get("teams")).get("home")));
        fixture.setAwayTeam(mapTeam((Map<String, Object>) ((Map<String, Object>) fixtureMap.get("teams")).get("away")));
        fixture.setVenue(
                mapVenue((Map<String, Object>) ((Map<String, Object>) fixtureMap.get("fixture")).get("venue")));
        fixture.setStatus(
                mapStatus((Map<String, Object>) ((Map<String, Object>) fixtureMap.get("fixture")).get("status")));
        fixture.setDate((String) ((Map<String, Object>) fixtureMap.get("fixture")).get("date"));
        fixture.setId(castLong(((Map<String, Object>) fixtureMap.get("fixture")).get("id")));
        fixture.setScore(mapScore((Map<String, Object>) fixtureMap.get("score")));
        return fixture;
    }

    private static League mapLeague(Map<String, Object> leagueMap) {
        if (leagueMap == null)
            return null;
        League league = new League();
        league.setId(castLong(leagueMap.get("id")));
        league.setName((String) leagueMap.get("name"));
        league.setCountry((String) leagueMap.get("country"));
        league.setLogoUrl((String) leagueMap.get("logo"));
        league.setFlagUrl((String) leagueMap.get("flag"));
        return league;
    }

    private static Team mapTeam(Map<String, Object> teamMap) {
        if (teamMap == null)
            return null;
        Team team = new Team();
        team.setId(castLong(teamMap.get("id")));
        team.setName((String) teamMap.get("name"));
        team.setLogoUrl((String) teamMap.get("logo"));
        team.setWinner(teamMap.get("winner") instanceof Boolean ? (Boolean) teamMap.get("winner") : null);
        return team;
    }

    private static Venue mapVenue(Map<String, Object> venueMap) {
        if (venueMap == null)
            return null;
        Venue venue = new Venue();
        venue.setId(castLong(venueMap.get("id")));
        venue.setName((String) venueMap.get("name"));
        venue.setCity((String) venueMap.get("city"));
        return venue;
    }

    private static String mapStatus(Map<String, Object> statusMap) {
        return Optional.ofNullable(statusMap)
                .map(m -> (String) m.get("long"))
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    private static Score mapScore(Map<String, Object> scoreMap) {
        if (scoreMap == null)
            return null;
        Map<String, Object> fulltime = (Map<String, Object>) scoreMap.get("fulltime");
        if (fulltime == null)
            return null;
        Score score = new Score();
        score.setHome(castInt(fulltime.get("home")));
        score.setAway(castInt(fulltime.get("away")));
        return score;
    }

    // --- Helper methods ---
    private static Long castLong(Object obj) {
        return obj instanceof Number ? ((Number) obj).longValue() : null;
    }

    private static Integer castInt(Object obj) {
        return obj instanceof Number ? ((Number) obj).intValue() : null;
    }
}
