package com.github.cotrod.hotel.model;

public class HotelRoomDTO {
    private Long id;
    private RoomType type;
    private int amountOfRooms;
    private int quantity;

    public HotelRoomDTO() {
    }

    public HotelRoomDTO(Long id, RoomType type, int amountOfRooms, int quantity) {
        this.id = id;
        this.type = type;
        this.amountOfRooms = amountOfRooms;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
