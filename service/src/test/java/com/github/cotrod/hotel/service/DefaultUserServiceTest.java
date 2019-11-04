package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.*;
import com.github.cotrod.hotel.service.impl.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultUserServiceTest {
    private UserLoginDTO userLoginDTO = new UserLoginDTO("user", "user");
    private UserLoginDTO wrongPassDTO = new UserLoginDTO("user", "wrong");
    private UserLoginDTO wrongLoginDTO = new UserLoginDTO("wrong", "wrong");
    private UserSignupDTO userSignupDTO = new UserSignupDTO("nlogin", "npass", "Константин", "Родной");
    private UserSignupDTO wrongSignupDTO = new UserSignupDTO("user", "npass", "Константин", "Родной");
    private UserDTO correctUserDTO = new UserDTO(3, "user", "user", Role.USER, "Константин");
    private UserDTO newCorrectUserDTO = new UserDTO(4, "nlogin", "user", Role.USER, "Константин");
    private ChangePassDTO changePassDTO = new ChangePassDTO("user", "npass", "npass");

    @Mock
    UserDao userDao;
    @InjectMocks
    DefaultUserService service;

    @Test
    void getUserTest() {
        when(userDao.getUserByLogin("user")).thenReturn(correctUserDTO);
        when(userDao.getUserByLogin("wrong")).thenReturn(null);
        UserDTO userDTO = service.getUser(userLoginDTO);
        assertEquals(correctUserDTO, userDTO);
        userDTO = service.getUser(wrongLoginDTO);
        assertNull(userDTO);
        userDTO = service.getUser(wrongPassDTO);
        assertNull(userDTO);
    }

    @Test
    void saveUserTest() {
        when(userDao.getUserByLogin("user")).thenReturn(correctUserDTO);
        when(userDao.getUserByLogin("nlogin")).thenReturn(null);
        when(userDao.save(userSignupDTO)).thenReturn(4L);
        when(userDao.getUserById(4L)).thenReturn(newCorrectUserDTO);
        UserDTO userDTO = service.saveUser(wrongSignupDTO);
        assertNull(userDTO);
        userDTO = service.saveUser(userSignupDTO);
        assertEquals(newCorrectUserDTO, userDTO);
    }

    @Test
    void changePasswordTest() {
        when(userDao.getUserById(3L)).thenReturn(correctUserDTO);
        assertTrue(service.changePassword(3L, changePassDTO));
    }
}
