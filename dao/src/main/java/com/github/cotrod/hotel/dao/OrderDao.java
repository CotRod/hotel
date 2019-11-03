package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;

import java.util.List;

public interface OrderDao {
    Long makeOrder(OrderCreateDTO order);

    List<OrderDTO> getOrders(Long userId, int startResult, int maxResult);

    void updateDecision(Long id, Decision decision);

    long getAmountOfOrders(Long userId);
}
