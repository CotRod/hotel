package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultOrderService implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(DefaultOrderService.class);
    private OrderDao orderDao;
    private HotelRoomDao roomDao;

    public DefaultOrderService(OrderDao orderDao, HotelRoomDao roomDao) {
        this.orderDao = orderDao;
        this.roomDao = roomDao;
    }

    @Override
    @Transactional
    public long makeOrder(OrderCreateDTO order) {
        HotelRoomDTO room = roomDao.getRoomById(order.getRoomId());
        if (room != null && room.getQuantity() > 0) {
            order.setDecision(Decision.AWAITING);
            return orderDao.makeOrder(order);
        }
        return -1L;
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrders(Long userId, Integer page) {
        return orderDao.getOrders(userId, page);
    }

    @Override
    @Transactional
    public void updateDecision(Long id, Decision decision) {
        orderDao.updateDecision(id, decision);
    }

    @Override
    @Transactional
    public boolean isNotLastPage(Long userId, int page) {
        return !orderDao.isLastPage(userId, page);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        orderDao.deleteOrder(orderId);
    }
}
