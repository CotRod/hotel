package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.dao.impl.DefaultHotelRoomDao;
import com.github.cotrod.hotel.dao.impl.DefaultOrderDao;
import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultOrderService implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(DefaultOrderService.class);
    private OrderDao orderDao = DefaultOrderDao.getInstance();
    private HotelRoomDao roomDao = DefaultHotelRoomDao.getInstance();

    private static class SingletonHolder {
        static final OrderService HOLDER_INSTANCE = new DefaultOrderService();
    }

    public static OrderService getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public long makeOrder(OrderCreateDTO order) {
        HotelRoomDTO room = roomDao.getRoomById(order.getRoomId());
        if (room != null && room.getQuantity() > 0) {
            return orderDao.makeOrder(order);
        }
        return -1;
    }

    @Override
    public List<OrderDTO> getOrders(long userId) {
        return orderDao.getOrders(userId);
    }

    @Override
    public void updateDecision(long id, Decision decision) {
        orderDao.updateDecision(id, decision);
    }
}
