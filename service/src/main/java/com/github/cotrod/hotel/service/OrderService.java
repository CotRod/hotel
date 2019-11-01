package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderAdminDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.OrderUserDTO;

import java.util.List;

public interface OrderService {
    long makeOrder(OrderDTO order);

    List<OrderUserDTO> getUserOrders(long userId);

    List<OrderAdminDTO> getAdminOrders();

    void updateDecision(long id, Decision decision);
}
