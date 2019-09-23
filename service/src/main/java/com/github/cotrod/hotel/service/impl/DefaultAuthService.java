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
        AuthService localInstance = instance;
        if (localInstance == null) {
            synchronized (AuthService.class) {
                if (localInstance == null) {
                    instance = localInstance = new DefaultAuthService();
                }
            }
        }
        return localInstance;
    }

    @Override
    public boolean isValidUser(String login, String password) {
        User user = getUserByLogin(login);
        return user != null && user.getPassword().equals(password);
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
