package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.config.DaoConfig;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultUserDaoTest {
    @Autowired
    UserDao dao;

    @Test
    void save() {
        Long id = dao.save(new UserSignupDTO("log", "pass", "Ольга", "привет", Role.USER));
        assertNotNull(id);
        String nameFromBD = dao.getUserById(id).getFirstName();
        String loginFromDB = dao.getUserById(id).getLogin();
        assertEquals("Ольга", nameFromBD);
        assertEquals("log", loginFromDB);
    }

    @Test
    void getUserByLogin() {
        dao.save(new UserSignupDTO("user", "pass", "Константин", "привет", Role.USER));
        String firstName = dao.getUserByLogin("user").getFirstName();
        UserDTO userNull = dao.getUserByLogin("user1");
        assertEquals("Константин", firstName);
        assertNull(userNull);
    }

    @Test
    void changePassword() {
        Long id = dao.save(new UserSignupDTO("user", "pass", "Константин", "привет", Role.USER));
        String newPass = "newPass";
        dao.changePassword(id, newPass);
        assertEquals(newPass, dao.getUserById(id).getPassword());
    }

    @Test
    void getUsers() {
        List<UserDTO> users = dao.getUsers(Role.USER);
        assertNotNull(users);
        assertEquals(0, users.size());
        dao.save(new UserSignupDTO("user", "pass", "Константин", "привет", Role.USER));
        users = dao.getUsers(Role.USER);
        assertEquals(1, users.size());
    }

    @Test
    void deleteUser() {
        Long id = dao.save(new UserSignupDTO("login", "pass", "Ольга", "привет", Role.USER));
        assertNotNull(dao.getUserById(id));
        dao.deleteUser(id);
        assertNull(dao.getUserById(id));
    }
}
