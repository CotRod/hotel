package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.model.*;

import java.time.LocalDate;

public class ConverterToDTO {
    public static HotelRoomDTO createHotelRoomDTO(HotelRoom roomFromDB) {
        Long id = roomFromDB.getId();
        RoomType type = roomFromDB.getType();
        int amountOfRooms = roomFromDB.getAmountOfRooms();
        int quantity = roomFromDB.getQuantity();
        return new HotelRoomDTO(id, type, amountOfRooms, quantity);
    }

    public static UserDTO createUserDTO(User userFromDB) {
        long id = userFromDB.getClient().getId();
        String login = userFromDB.getLogin();
        String password = userFromDB.getPassword();
        Role role = userFromDB.getRole();
        String firstName = userFromDB.getClient().getFirstName();
        return new UserDTO(id, login, password, role, firstName);
    }

    public static OrderDTO createOrderDTO(Order order) {
        Long orderId = order.getId();
        LocalDate dateIn = order.getDateIn();
        LocalDate dateOut = order.getDateOut();
        String login = order.getClient().getUser().getLogin();
        RoomType type = order.getHotelRoom().getType();
        int amountOfRooms = order.getHotelRoom().getAmountOfRooms();
        Decision decision = order.getDecision();
        return new OrderDTO(orderId, dateIn, dateOut, login, type, amountOfRooms, decision);
    }
}
