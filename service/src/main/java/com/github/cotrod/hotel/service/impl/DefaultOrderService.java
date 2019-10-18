package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.dao.impl.DefaultHotelRoomDao;
import com.github.cotrod.hotel.dao.impl.DefaultOrderDao;
import com.github.cotrod.hotel.model.HotelRoom;
import com.github.cotrod.hotel.model.Order;
import com.github.cotrod.hotel.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public long makeOrder(Order order) {
        HotelRoom room = roomDao.getRoomById(order.getRoomId());
        if (room != null && room.getQuantity() > 0) {
            return orderDao.makeOrder(order);
        }
        return -1;
    }
}
