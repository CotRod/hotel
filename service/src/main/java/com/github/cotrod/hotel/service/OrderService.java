package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;

import java.util.List;

public interface OrderService {
    long makeOrder(OrderCreateDTO order);

    List<OrderDTO> getOrders(Long userId, Integer page);

    void updateDecision(Long id, Decision decision);

    boolean isNotLastPage(Long userId, int page);
}
