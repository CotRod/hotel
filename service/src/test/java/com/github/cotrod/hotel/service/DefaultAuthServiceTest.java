package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.service.impl.DefaultAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultAuthServiceTest {
    @Mock
    UserDao dao;
    @InjectMocks
    DefaultAuthService service;

    @Test
    void testLoginCorrect() {
        when(dao.getUserByLogin("admin")).thenReturn(new User("admin", "admin", Role.ADMIN));
        User user = service.getUser("admin", "admin");
        assertNotNull(user);
        assertEquals("admin", user.getPassword());
        assertEquals("ADMIN", user.getRole().name());
    }

    @Test
    void wrongPassword() {
        when(dao.getUserByLogin("user")).thenReturn(new User("user", "user", Role.USER));
        User user = service.getUser("user", "admin");
        assertNull(user);
    }

    @Test
    void saveExistUser() {
        when(dao.getUserByLogin("admin")).thenReturn(new User("admin", "admin", Role.ADMIN));
        User user = service.saveUser("admin", "123");
        assertNull(user);
    }

    @Test
    void getUsers() {
        when(dao.getUsers()).thenReturn(new ArrayList<User>());
        List<User> users = service.getUsers();
        assertNotNull(users);
    }

    @Test
    void changeWrongPass(){
        when(dao.getUserByLogin("user")).thenReturn(new User("user","user"));
        assertTrue(service.changePassword(new User("user","user"),"npass","npass"));
        assertFalse(service.changePassword(new User("user","user"),"npass","anpass"));
        assertFalse(service.changePassword(new User("user","wrongpass"),"npass","npass"));
    }
}
