package com.pulseplay.app.model;

import lombok.Data;

@Data
public class League {
    private Long id;
    private String name;
    private String country;
    private String logoUrl;
    private String flagUrl;
}
