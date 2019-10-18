package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.Order;

public interface OrderDao {
    long makeOrder(Order order);
}
