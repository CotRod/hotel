package com.github.cotrod.hotel.model;

import java.time.LocalDate;

public class OrderDTO {
    private Long id;
    private Long clientId;
    private Long roomId;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private Decision decision;

    public OrderDTO() {
    }

    public OrderDTO(Long roomId, Long clientId, LocalDate dateIn, LocalDate dateOut) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
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
