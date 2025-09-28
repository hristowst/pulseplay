package com.pulseplay.app.model;

import lombok.Data;

@Data
public class Team {
    private Long id;
    private String name;
    private String logoUrl;
    private Boolean winner;
}
