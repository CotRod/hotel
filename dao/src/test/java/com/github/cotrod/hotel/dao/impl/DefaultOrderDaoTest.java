package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.config.DaoConfig;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.dao.repository.HotelRoomRepository;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.RoomType;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
class DefaultOrderDaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    HotelRoomRepository roomRepository;

    @Test
    void makeOrder() {
        Long clientId = userDao.save(new UserSignupDTO("orderUser", "user", "Konst", "Rodnoy"));
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setType(RoomType.STANDARD);
        hotelRoom.setAmountOfRooms(2);
        hotelRoom.setQuantity(5);
        HotelRoom hotelRoom1 = roomRepository.save(hotelRoom);
        Long hotelRoomId = hotelRoom1.getId();

        OrderCreateDTO orderDTO = new OrderCreateDTO(hotelRoomId, clientId, LocalDate.now(), LocalDate.now());

        Long id = orderDao.makeOrder(orderDTO);
        assertNotNull(id);
        assertEquals(4, roomRepository.getOne(hotelRoomId).getQuantity());
    }

    @Test
    void getOrders() {
        Long userId = userDao.save(new UserSignupDTO("orderUser", "user", "Konst", "Rodnoy"));
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setType(RoomType.STANDARD);
        hotelRoom.setAmountOfRooms(2);
        hotelRoom.setQuantity(5);
        HotelRoom hotelRoom1 = roomRepository.save(hotelRoom);
        Long hotelRoomId = hotelRoom1.getId();

        OrderCreateDTO orderDTO = new OrderCreateDTO(hotelRoomId, userId, LocalDate.now(), LocalDate.now());
        orderDao.makeOrder(orderDTO);
        List<OrderDTO> orders = orderDao.getOrders(2L, 0);
        assertNotNull(orders);
        assertEquals(0, orders.size());
        orders = orderDao.getOrders(0L, 0);
        assertNotNull(orders);
        assertEquals(1, orders.size());
        orders = orderDao.getOrders(0L, 0);
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertTrue(orderDao.isLastPage(1L, 1));
    }
}