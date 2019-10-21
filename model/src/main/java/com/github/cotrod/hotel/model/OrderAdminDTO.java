package com.github.cotrod.hotel.model;

import java.time.LocalDate;

public class OrderAdminDTO {
    private long orderId;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private String login;
    private RoomType type;
    private int amountOfRooms;
    private Decision decision;

    public OrderAdminDTO(long orderId, LocalDate dateIn, LocalDate dateOut, String login, RoomType type, int amountOfRooms, Decision decision) {
        this.orderId = orderId;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.login = login;
        this.type = type;
        this.amountOfRooms = amountOfRooms;
        this.decision = decision;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getAmountOfRooms() {
        return amountOfRooms;
    }

    public void setAmountOfRooms(int amountOfRooms) {
        this.amountOfRooms = amountOfRooms;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
