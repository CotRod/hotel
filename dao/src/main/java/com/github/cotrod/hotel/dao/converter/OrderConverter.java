package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.RoomType;

import java.time.LocalDate;

public class OrderConverter {
    public static OrderDTO fromEntity(Order order) {
        if (order == null) {
            return null;
        }
        Long orderId = order.getId();
        LocalDate dateIn = order.getDateIn();
        LocalDate dateOut = order.getDateOut();
        String login = order.getClient().getUser().getLogin();
        RoomType type = order.getHotelRoom().getType();
        int amountOfRooms = order.getHotelRoom().getAmountOfRooms();
        Decision decision = order.getDecision();
        return new OrderDTO(orderId, dateIn, dateOut, login, type, amountOfRooms, decision);
    }

    public static Order toEntity(OrderCreateDTO orderCreateDTO) {
        if (orderCreateDTO == null) {
            return null;
        }
        Order order = new Order();
        order.setDateIn(orderCreateDTO.getDateIn());
        order.setDateOut(orderCreateDTO.getDateOut());
        order.setDecision(orderCreateDTO.getDecision());
        return order;
    }
}
