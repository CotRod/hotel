package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultUserDao implements UserDao {
    private Map<String,User> usersMap;

    private static volatile UserDao instance;

    private DefaultUserDao(){
        this.usersMap = new HashMap<>();
        save(new User("admin","admin"));
    }

    public static UserDao getInstance(){
        if(instance==null){
            synchronized (UserDao.class){
                if(instance==null){
                    instance = new DefaultUserDao();
                }
            }
        }
        return instance;
    }

    @Override
    public void save(User user) {
        usersMap.put(user.getLogin(),user);
    }

    @Override
    public User getUserByLogin(String login) {
        return usersMap.get(login);
    }
}
