package com.pulseplay.app.dto;

public record TeamDTO(
        Long id,
        String name,
        String logoUrl,
        Boolean winner) {
}