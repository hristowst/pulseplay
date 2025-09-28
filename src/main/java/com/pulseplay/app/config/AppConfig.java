package com.pulseplay.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${football.base-url}")
    String footballBaseUrl;
    @Value("${football.api.key}")
    String footballApiKey;

    @Bean
    WebClient.Builder webClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(2 * 1024 * 1024)); // Set to 2MB
    }
}
