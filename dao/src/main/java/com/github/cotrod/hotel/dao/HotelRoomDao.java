package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.HotelRoomDTO;

import java.util.List;

public interface HotelRoomDao {
    List<HotelRoomDTO> getRooms();

    HotelRoomDTO getRoomById(Long id);
}
