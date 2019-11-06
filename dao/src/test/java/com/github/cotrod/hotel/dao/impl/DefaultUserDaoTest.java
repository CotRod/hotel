package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultUserDaoTest {

    @BeforeAll
    static void createTestDB() {
        DefaultUserDao.getInstance().save(new UserSignupDTO("user", "user", "Константин", "Родной"));
    }

    @Test
    void save() {
        Long id = DefaultUserDao.getInstance().save(new UserSignupDTO("log", "pass", "Ольга", "привет"));
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
    void getUsers() {
        List<UserDTO> users = DefaultUserDao.getInstance().getUsers(Role.USER);
        assertNotNull(users);
    }

    @Test
    void deleteUser() {
        Long id = DefaultUserDao.getInstance().save(new UserSignupDTO("login", "pass", "Ольга", "привет"));
        assertNotNull(DefaultUserDao.getInstance().getUserById(id));
        DefaultUserDao.getInstance().deleteUser(id);
        assertNull(DefaultUserDao.getInstance().getUserById(id));
    }

    @AfterAll
    static void clear() {
        EntityManager em = EMUtil.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
}
