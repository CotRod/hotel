package com.github.cotrod.hotel.model;

public enum RoomType {
    STANDARD("Standard"), DELUXE("Deluxe"), STUDIO("Studio");
    private String type;

    RoomType(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }
}
