package com.github.cotrod.hotel.model;

import java.time.LocalDate;

public class Order {
    private long id;
    private long clientId;
    private long roomId;
    private LocalDate dateMoveIn;
    private LocalDate dateMoveOut;
    private Decision decision;

    public Order() {
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

    public LocalDate getDateMoveIn() {
        return dateMoveIn;
    }

    public void setDateMoveIn(LocalDate dateMoveIn) {
        this.dateMoveIn = dateMoveIn;
    }

    public LocalDate getDateMoveOut() {
        return dateMoveOut;
    }

    public void setDateMoveOut(LocalDate dateMoveOut) {
        this.dateMoveOut = dateMoveOut;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
