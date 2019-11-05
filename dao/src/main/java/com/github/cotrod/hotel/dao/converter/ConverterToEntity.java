package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;

public class ConverterToEntity {
    public static User createUserEntity(UserSignupDTO userSignupDTO) {
        User user = new User();
        user.setLogin(userSignupDTO.getLogin());
        user.setPassword(userSignupDTO.getPassword());
        user.setRole(userSignupDTO.getRole());
        return user;
    }

    public static Client createClientEntity(UserSignupDTO userSignupDTO) {
        Client client = new Client();
        client.setFirstName(userSignupDTO.getFirstName());
        client.setLastName(userSignupDTO.getLastName());
        return client;
    }

    public static Order createOrderEntity(OrderCreateDTO orderCreateDTO) {
        Order order = new Order();
        order.setDateIn(orderCreateDTO.getDateIn());
        order.setDateOut(orderCreateDTO.getDateOut());
        order.setDecision(orderCreateDTO.getDecision());
        return order;
    }
}
