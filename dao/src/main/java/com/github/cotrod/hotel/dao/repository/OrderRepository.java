package com.github.cotrod.hotel.dao.repository;

import com.github.cotrod.hotel.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countOrdersByClient_Id(Long id);
}
