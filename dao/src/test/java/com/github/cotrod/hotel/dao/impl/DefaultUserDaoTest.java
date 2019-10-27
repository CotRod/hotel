package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.model.UserSignupDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultUserDaoTest {
    Long id;

    @Test
    void save() {
        id = DefaultUserDao.getInstance().save(new UserSignupDTO("log", "pass", "Ольга", "привет"));
        String nameFromBD = DefaultUserDao.getInstance().getUserById(id).getFirstName();
        assertEquals("Ольга", nameFromBD);
    }

    @AfterAll
    public static void deleteUser() {
        DefaultUserDao.getInstance().deleteUser(DefaultUserDao.getInstance().getUserByLogin("log").getId());
    }
}
