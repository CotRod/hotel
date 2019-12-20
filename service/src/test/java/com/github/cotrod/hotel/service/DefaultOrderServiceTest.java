package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.RoomType;
import com.github.cotrod.hotel.service.impl.DefaultOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultOrderServiceTest {
    private OrderCreateDTO createDTO = new OrderCreateDTO(1L, 3L, LocalDate.now(), LocalDate.now());
    private HotelRoomDTO roomDTO = new HotelRoomDTO(1L, RoomType.STANDARD, 2, 5);

    @Mock
    OrderDao orderDao;
    @Mock
    HotelRoomDao roomDao;
    @InjectMocks
    DefaultOrderService service;

    @Test
    void makeOrderTest() {
        when(roomDao.getRoomById(1L)).thenReturn(roomDTO);
        when(orderDao.makeOrder(createDTO)).thenReturn(1L);
        assertEquals(1L, service.makeOrder(createDTO));
        when(roomDao.getRoomById(2L)).thenReturn(null);
        assertEquals(-1L, service.makeOrder(new OrderCreateDTO(2L, 2L, LocalDate.now(), LocalDate.now())));
    }

    @Test
    void getOrdersTest() {
        when(orderDao.getOrders(3L, 0)).thenReturn(null);
        assertNull(service.getOrders(3L, 0));
    }
}
