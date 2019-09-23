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
    public void isExist(){
        Assertions.assertTrue(DefaultAuthService.getInstance().isUserExist("admin","admin"));
    }
    @Test
    public void isNotExist(){
        Assertions.assertFalse(DefaultAuthService.getInstance().isUserExist("User","user"));
        Assertions.assertFalse(DefaultAuthService.getInstance().isUserExist("Admin","admin"));
    }
}
