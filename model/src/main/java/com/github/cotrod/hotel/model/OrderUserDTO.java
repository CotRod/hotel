package com.github.cotrod.hotel.model;

import java.time.LocalDate;

public class OrderUserDTO {
    private long orderId;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private RoomType type;
    private int amountOfRooms;
    private Decision decision;

    public OrderUserDTO(long orderId, LocalDate dateIn, LocalDate dateOut, RoomType type, int amountOfRooms, Decision decision) {
        this.orderId = orderId;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.type = type;
        this.amountOfRooms = amountOfRooms;
        this.decision = decision;
    }

    public long getOrderId() {
        return orderId;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public RoomType getType() {
        return type;
    }

    public int getAmountOfRooms() {
        return amountOfRooms;
    }

    public Decision getDecision() {
        return decision;
    }
}
