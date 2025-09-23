package com.puleplay.pulseplay.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
@GetMapping("/profile")
    public Map<String, Object> getProfile(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
            "userId", jwt.getClaimAsString("sub"),
            "email", jwt.getClaimAsString("email"),
            "role", jwt.getClaimAsString("role")
        );
    }
}
