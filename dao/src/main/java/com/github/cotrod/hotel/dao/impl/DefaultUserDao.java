package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.User;

import java.sql.*;
import java.util.ResourceBundle;

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

    public Connection connect() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("db");
        String url = resource.getString("url");
        String user = resource.getString("user");
        String password = resource.getString("password");
        return DriverManager.getConnection(url,user,password);
    }

    @Override
    public void save(User user) {
        UserDao dataBase = DefaultUserDao.getInstance();
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
        UserDao dateBase = DefaultUserDao.getInstance();
        try (Connection connection = dateBase.connect();
        PreparedStatement statement = connection.prepareStatement("select * from user where login =?")){
            statement.setString(1,login);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    return new User(login,rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
