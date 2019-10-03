package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
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
        MySqlDataBase dataBase = new MySqlDataBase();
        try (Connection connection = dataBase.connect();
             PreparedStatement statement = connection.prepareStatement("insert into user(login,password) values (?,?)")){
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByLogin(String login) {
        return usersMap.get(login);
    }
}
