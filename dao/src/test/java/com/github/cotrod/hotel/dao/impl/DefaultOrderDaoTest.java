package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.RoomType;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultOrderDaoTest {
    @BeforeAll
    static void createTables() {
        DefaultUserDao.getInstance().save(new UserSignupDTO("orderUser", "user", "Konst", "Rodnoy"));
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setType(RoomType.STANDARD);
        hotelRoom.setAmountOfRooms(2);
        hotelRoom.setQuantity(5);
        Session session = EMUtil.getEntityManager().getSession();
        session.beginTransaction();
        session.save(hotelRoom);
        session.getTransaction().commit();
        session.close();
        OrderCreateDTO orderDTO = new OrderCreateDTO(1L, 1L, LocalDate.now(), LocalDate.now());
        Long id = DefaultOrderDao.getInstance().makeOrder(orderDTO);
    }

    @Test
    void makeOrder() {
        OrderCreateDTO orderDTO = new OrderCreateDTO(1L, 1L, LocalDate.now(), LocalDate.now());
        Long id = DefaultOrderDao.getInstance().makeOrder(orderDTO);
    }

    @Test
    void getAdminOrders() {
        List<OrderDTO> orders = DefaultOrderDao.getInstance().getOrders(0L, 0, 3);
        assertNotNull(orders);
        orders = DefaultOrderDao.getInstance().getOrders(1L, 0, 3);
        assertNotNull(orders);
    }

    @AfterAll
    static void clear() {
        EntityManager em = EMUtil.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
}