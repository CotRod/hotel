package com.github.cotrod.hotel.web.rq;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CreateOrderRq {
    private Long roomId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateIn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOut;

    public Long getRoomId() {
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
}
