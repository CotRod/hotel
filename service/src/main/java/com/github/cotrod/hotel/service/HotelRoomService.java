package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.HotelRoomDTO;

import java.util.List;

public interface HotelRoomService {
    List<HotelRoomDTO> getRooms();
}
