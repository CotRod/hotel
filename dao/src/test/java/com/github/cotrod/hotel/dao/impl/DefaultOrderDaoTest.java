package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.RoomType;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DefaultOrderDaoTest {
    @BeforeAll
    static void createTables() {
        DefaultUserDao.getInstance().save(new UserSignupDTO("user", "user", "Konst", "Rodnoy"));
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
    void makeOrder() {
        OrderDTO orderDTO = new OrderDTO(1L, 1L, LocalDate.now(), LocalDate.now());
        Long id = DefaultOrderDao.getInstance().makeOrder(orderDTO);
    }
}