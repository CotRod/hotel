package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.model.Order;
import com.github.cotrod.hotel.model.OrderUserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultOrderDaoTest {

    @Test
    void makeOrder() {
        Order order = new Order();
        LocalDate nowDate = LocalDate.now();
        order.setDateIn(nowDate);
        order.setDateOut(LocalDate.now());
        order.setClientId(2);
        order.setRoomId(2);
        long id = DefaultOrderDao.getInstance().makeOrder(order);

        List<OrderUserDTO> userOrders = DefaultOrderDao.getInstance().getUserOrders(2);
        OrderUserDTO orderUserDTO = userOrders.get(userOrders.size() - 1);
        LocalDate dateInfromDB = orderUserDTO.getDateIn();
        assertEquals(dateInfromDB, nowDate);


    }
}