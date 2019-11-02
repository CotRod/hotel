package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.RoomType;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultHotelRoomDaoTest {
    @BeforeAll
    static void createDB() {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setType(RoomType.STANDARD);
        hotelRoom.setAmountOfRooms(2);
        hotelRoom.setQuantity(5);
        Session session = EMUtil.getEntityManager().getSession();
        session.beginTransaction();
        session.save(hotelRoom);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void getRooms() {
        List<HotelRoomDTO> rooms = DefaultHotelRoomDao.getInstance().getRooms();
        assertNotNull(rooms);
    }

    @Test
    void getRoomById() {
        HotelRoomDTO roomDTO = DefaultHotelRoomDao.getInstance().getRoomById(1L);
        assertNotNull(roomDTO);
    }

    @AfterAll
    static void clear() {
        EntityManager em = EMUtil.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
}