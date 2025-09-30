package com.pulseplay.app.provider.football;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.pulseplay.app.model.Fixture;
import com.pulseplay.app.provider.IOddsProvider;

@Component
public class FootballOddsProvider implements IOddsProvider {

        static final String NOT_STARTED_STATUS = "Not Started";

        private MultiValueMap<String, String> countryLeageMap = new LinkedMultiValueMap<>() {
                {
                        put("England", List.of("Premier League", "Championship", "FA Cup", "League Cup"));
                        put("Spain", List.of("LA Liga", "Super Cup", "Segunda División", "Copa del Rey"));
                        put("Germany", List.of("Bundesliga", "2. Bundesliga", "Super Cup"));
                        put("Italy", List.of("Serie A", "Coppa Italia", "Serie B"));
                        put("France", List.of("Ligue 1", "Ligue 2", "Coupe de France"));
                        put("Bulgaria", List.of("First League", "Second League", "Cup", "Bulgarian Supercup"));
                }
        };

        private final WebClient webClient;

        public FootballOddsProvider(WebClient.Builder webClientBuilder,
                        @Value("${football.base-url}") String baseUrl,
                        @Value("${football.api.key}") String apiKey) {
                this.webClient = webClientBuilder
                                .baseUrl(baseUrl)
                                .defaultHeader("x-rapidapi-host", "v3.football.api-sports.io")
                                .defaultHeader("x-rapidapi-key", apiKey)
                                .build();
        }

        @Override
        public Map<String, Object> getOdds(String matchId) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .pathSegment("odds")
                                                .queryParam("fixture", matchId)
                                                .build())
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                                })
                                .block();
        }

        @Override
        public String getVendorName() {
                return "api-football.com";
        }

        @Override
        public List<Fixture> getFixtures(String date) {
                var responseMap = (List<Map<String, Object>>) webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .pathSegment("fixtures")
                                                .queryParam("date", date)
                                                .build())
                                .retrieve()
                                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                                })
                                .block().get("response");

                if (responseMap.isEmpty()) {
                        throw new RuntimeException("No fixtures found for the given parameters");
                }

                try {
                        var response = FootballOddsMapper.mapApiResponseToFixtures(responseMap);
                        return response.stream()
                                        .filter(fixture -> {
                                                var league = fixture.getLeague().getName();
                                                var country = fixture.getLeague().getCountry();
                                                var status = fixture.getStatus();
                                                return countryLeageMap.containsKey(country) &&
                                                                countryLeageMap.get(country).contains(league) &&
                                                                status.equals(NOT_STARTED_STATUS);
                                        })
                                        .toList();
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                }
        }

}
