package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultUserDao implements UserDao {

    private static volatile UserDao instance;

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
        MySqlDataBase dataBase = new MySqlDataBase();
        try (Connection connection = dataBase.connect();
             PreparedStatement statement = connection.prepareStatement("insert into user_table(login,password,role) values (?,?,?)")) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByLogin(String login) {
        MySqlDataBase dataBase = new MySqlDataBase();
        try (Connection connection = dataBase.connect();
             PreparedStatement statement = connection.prepareStatement("select * from user_table where login =?")) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new User(login, rs.getString("password"), Role.valueOf(rs.getString("role")));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
