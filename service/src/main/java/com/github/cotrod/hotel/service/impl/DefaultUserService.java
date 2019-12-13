package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.*;
import com.github.cotrod.hotel.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDTO getUser(UserLoginDTO userLogin) {
        UserDTO userDTO = getUserByLogin(userLogin.getLogin());
        if (userDTO != null) {
            if (userDTO.getPassword().equals(userLogin.getPassword())) {
                return userDTO;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public UserDTO saveUser(UserSignupDTO userSignup) {
        UserDTO userFromBD = getUserByLogin(userSignup.getLogin());
        if (userFromBD == null) {
            userSignup.setRole(Role.USER);
            long id = userDao.save(userSignup);
            return userDao.getUserById(id);
        }
        return null;
    }

    @Override
    @Transactional
    public UserDTO getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    @Transactional
    public List<UserDTO> getUsers() {
        Role role = Role.USER;
        return userDao.getUsers(role);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public boolean changePassword(long id, ChangePassDTO changePassDTO) {
        if (changePassDTO.getNewPass1().equals(changePassDTO.getNewPass2())) {
            UserDTO userDTO = userDao.getUserById(id);
            if (userDTO.getPassword().equals(changePassDTO.getOldPass())) {
                userDao.changePassword(id, changePassDTO.getNewPass1());
                return true;
            }
        }
        return false;
    }
}
