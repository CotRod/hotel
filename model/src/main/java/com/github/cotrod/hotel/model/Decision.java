package com.github.cotrod.hotel.model;

public enum Decision {
    UNDERCON("Under consideration"), APPROVED("Approved");
    private String decision;

    Decision(String dec) {
        decision = dec;
    }

    String getDecision() {
        return decision;
    }
}
