package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.impl.DefaultUserDao;
import com.github.cotrod.hotel.model.ChangePassDTO;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserLoginDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import com.github.cotrod.hotel.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {
    private UserDao userDao = DefaultUserDao.getInstance();

    private static class SingletonHolder {
        static final UserService HOLDER_INSTANCE = new DefaultUserService();
    }

    public static UserService getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
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
    public UserDTO saveUser(UserSignupDTO userSignup) {
        UserDTO userFromBD = getUserByLogin(userSignup.getLogin());
        if (userFromBD == null) {
            long id = userDao.save(userSignup);
            return userDao.getUserById(id);
        }
        return null;
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
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
