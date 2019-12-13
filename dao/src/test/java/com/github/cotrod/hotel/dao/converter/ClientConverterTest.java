package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientConverterTest {

    @Test
    void toEntity() {
        Client clientNull = ClientConverter.toEntity(null);
        assertNull(clientNull);
        UserSignupDTO userSignupDTO = new UserSignupDTO("Login", "Password", "FName", "LName");
        Client clientNotNull = ClientConverter.toEntity(userSignupDTO);
        assertNotNull(clientNotNull);
        assertEquals(clientNotNull.getFirstName(), userSignupDTO.getFirstName());
        assertEquals(clientNotNull.getLastName(), userSignupDTO.getLastName());
    }
}