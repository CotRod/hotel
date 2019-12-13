package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.RoomType;

public class HotelRoomConverter {
    public static HotelRoomDTO fromEntity(HotelRoom roomFromDB) {
        if (roomFromDB == null) {
            return null;
        }
        Long id = roomFromDB.getId();
        RoomType type = roomFromDB.getType();
        int amountOfRooms = roomFromDB.getAmountOfRooms();
        int quantity = roomFromDB.getQuantity();
        return new HotelRoomDTO(id, type, amountOfRooms, quantity);
    }
}
