package com.github.cotrod.hotel.model;

import java.time.LocalDate;

public class Order {
    private long id;
    private long clientId;
    private long roomId;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private Decision decision;

    public Order() {
    }

    public Order(long roomId, long clientId, LocalDate dateIn, LocalDate dateOut) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
