package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.impl.DefaultUserDao;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.service.AuthService;

import static com.github.cotrod.hotel.model.Role.*;

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
    public User getUser(String login, String password) {
        User user = getUserByLogin(login);
        if(user!=null){
            if (user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User saveUser(String login, String password) {
        User user = getUserByLogin(login);
        if (user==null){
            user = new User(login,password);
            userDao.save(user);
            return user;
        }else {
            return null;
        }
    }

    private User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

}
