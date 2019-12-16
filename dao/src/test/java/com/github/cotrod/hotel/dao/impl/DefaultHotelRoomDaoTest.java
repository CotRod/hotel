package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.config.DaoConfig;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.dao.repository.HotelRoomRepository;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
class DefaultHotelRoomDaoTest {
    @Autowired
    HotelRoomDao roomDao;
    @Autowired
    HotelRoomRepository repository;

    @Test
    void getRooms() {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setType(RoomType.STANDARD);
        hotelRoom.setAmountOfRooms(2);
        hotelRoom.setQuantity(5);
        repository.save(hotelRoom);

        List<HotelRoomDTO> rooms = roomDao.getRooms();
        assertNotNull(rooms);
        assertEquals(1, rooms.size());
    }

    @Test
    void getRoomById() {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setType(RoomType.STANDARD);
        hotelRoom.setAmountOfRooms(2);
        hotelRoom.setQuantity(5);
        repository.save(hotelRoom);
        HotelRoomDTO roomDTO = roomDao.getRoomById(1L);
        assertNotNull(roomDTO);
    }
}