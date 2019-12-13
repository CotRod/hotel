package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.model.UserSignupDTO;

public class ClientConverter {
    public static Client toEntity(UserSignupDTO userSignupDTO) {
        if (userSignupDTO == null) {
            return null;
        }
        Client client = new Client();
        client.setFirstName(userSignupDTO.getFirstName());
        client.setLastName(userSignupDTO.getLastName());
        return client;
    }
}