package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserConverterTest {

    @Test
    void fromEntity() {
        User user = new User();
        user.setId(1L);
        user.setLogin("login");
        user.setPassword("pass");
        user.setRole(Role.USER);
        Client client = new Client();
        client.setFirstName("fName");
        client.setId(1L);
        user.setClient(client);
        UserDTO userDTONull = UserConverter.fromEntity(null);
        UserDTO userDTO = UserConverter.fromEntity(user);
        assertNotNull(userDTO);
        assertNull(userDTONull);
    }

    @Test
    void toEntity() {
        User user = UserConverter.toEntity(new UserSignupDTO("login", "pass", "fname", "lname", Role.USER));
        User userNull = UserConverter.toEntity(null);
        assertNull(userNull);
        assertNotNull(user);
    }
}