package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderConverterTest {

    @Test
    void fromEntity() {
        OrderDTO orderDTONull = OrderConverter.fromEntity(null);
        assertNull(orderDTONull);
    }

    @Test
    void toEntity() {
        OrderCreateDTO orderCreateDTODTO = new OrderCreateDTO(1L, 1L, LocalDate.now(), LocalDate.now());
        orderCreateDTODTO.setDecision(Decision.AWAITING);
        Order orderNull = OrderConverter.toEntity(null);
        Order orderNotNull = OrderConverter.toEntity(orderCreateDTODTO);
        assertNull(orderNull);
        assertNotNull(orderNotNull);
    }
}