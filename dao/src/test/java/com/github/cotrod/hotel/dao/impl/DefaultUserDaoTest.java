package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DefaultUserDaoTest {
    Long id;

    @BeforeAll
    static void createTestDB() {
        DefaultUserDao.getInstance().save(new UserSignupDTO("user", "user", "Константин", "Родной"));
    }

    @Test
    void save() {
        id = DefaultUserDao.getInstance().save(new UserSignupDTO("log", "pass", "Ольга", "привет"));
        String nameFromBD = DefaultUserDao.getInstance().getUserById(id).getFirstName();
        assertEquals("Ольга", nameFromBD);
    }

    @Test
    void getUserByLogin() {
        String firstName = DefaultUserDao.getInstance().getUserByLogin("user").getFirstName();
        UserDTO userNull = DefaultUserDao.getInstance().getUserByLogin("user1");
        assertEquals("Константин", firstName);
        assertNull(userNull);
    }

    @Test
    void changePassword() {
        String newPass = "newPass";
        DefaultUserDao.getInstance().changePassword(1L, newPass);
        assertEquals(newPass, DefaultUserDao.getInstance().getUserById(1L).getPassword());
    }

    @Test
    void deleteUser() {
        DefaultUserDao.getInstance().deleteUser(1L);
    }
}
