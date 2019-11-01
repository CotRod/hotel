package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderAdminDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.OrderUserDTO;

import java.util.List;

public interface OrderDao {
    Long makeOrder(OrderDTO order);

    List<OrderUserDTO> getUserOrders(Long userId);

    List<OrderAdminDTO> getAdminOrders();

    void updateDecision(Long id, Decision decision);
}
