//package com.github.cotrod.hotel.dao.impl;
//
//import com.github.cotrod.hotel.model.UserSignupDTO;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class DefaultUserDaoTest {
//    UserSignupDTO testUserSignupDTO = new UserSignupDTO("testLog","testPass", "testName","testSurN");
//    String newPass = "newTestPass";
//
//    @Test
//    void save() {
//        assertNotNull(DefaultUserDao.getInstance().save(testUserSignupDTO));
//    }
//    @Test
//    void getUserByLogin(){
//        assertNotNull(DefaultUserDao.getInstance().getUserByLogin("testLog"));
//    }
//    @Test
//    void getUserAdmin() {
//        assertEquals("admin", DefaultUserDao.getInstance().getUserByLogin("admin").getLogin());
//        assertEquals("admin", DefaultUserDao.getInstance().getUserByLogin("admin").getPassword());
//        assertEquals("ADMIN", DefaultUserDao.getInstance().getUserByLogin("admin").getRole().name());
//        assertNotNull(DefaultUserDao.getInstance().getUserByLogin("admin"));
//    }
//
//    @Test
//    void getUsersList() {
//        assertNotNull(DefaultUserDao.getInstance().getUsers());
//    }
//}
