package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultUserDao implements UserDao {
    private Map<String, User> usersMap;

    private static volatile UserDao instance;

    private DefaultUserDao() {
        this.usersMap = new HashMap<>();
        save(new User("admin", "admin"));
    }

    public static UserDao getInstance() {
        UserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (UserDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultUserDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void save(User user) {
        usersMap.put(user.getLogin(), user);
    }

    @Override
    public User getUserByLogin(String login) {
        return usersMap.get(login);
    }
}
