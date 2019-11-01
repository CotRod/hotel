package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.model.HotelRoomDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

class DefaultHotelRoomDaoTest {

    @Test
    void getRooms() {
        List<HotelRoomDTO> rooms = DefaultHotelRoomDao.getInstance().getRooms();
        assertNull(rooms);
    }

    @Test
    void getRoomById() {
        HotelRoomDTO roomDTO = DefaultHotelRoomDao.getInstance().getRoomById(1L);
        assertNull(roomDTO);
    }
}