package com.github.cotrod.hotel.model;

import java.time.LocalDate;

public class OrderDTO {
    long orderId;
    LocalDate dateIn;
    LocalDate dateOut;
    RoomType type;
    int amountOfRooms;

    public OrderDTO(long orderId, LocalDate dateIn, LocalDate dateOut, RoomType type, int amountOfRooms) {
        this.orderId = orderId;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.type = type;
        this.amountOfRooms = amountOfRooms;
    }
}
