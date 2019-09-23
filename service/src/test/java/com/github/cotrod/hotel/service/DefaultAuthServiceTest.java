package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.service.impl.DefaultAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultAuthServiceTest {
    @Test
    public void getUser(){
        Assertions.assertNull(DefaultAuthService.getInstance().getUserByLogin("User"));
        Assertions.assertEquals("admin", DefaultAuthService.getInstance().getUserByLogin("admin").getLogin());
    }
    @Test
    public void isValidUser(){
        Assertions.assertFalse(DefaultAuthService.getInstance().isValidUser("wrongLogin","admin"));
        Assertions.assertFalse(DefaultAuthService.getInstance().isValidUser("admin","wrongPassword"));
        Assertions.assertTrue(DefaultAuthService.getInstance().isValidUser("admin","admin"));
    }
}
