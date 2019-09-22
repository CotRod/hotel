package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.impl.DefaultUserDao;
import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.service.AuthService;

public class DefaultAuthService implements AuthService {
    private UserDao userDao = DefaultUserDao.getInstance();
    private static volatile AuthService instance;

    private DefaultAuthService() {
    }

    public static AuthService getInstance() {
        if (instance == null) {
            synchronized (AuthService.class) {
                if (instance == null) {
                    instance = new DefaultAuthService();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isUserExist(String login, String password) {
        User user = getUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public void saveUser(String login, String password) {
        userDao.save(new User(login, password));
    }
}
