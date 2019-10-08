package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao implements UserDao {

    private static class SingletonHolder{
        static final DefaultUserDao HOLDER_INSTANCE = new DefaultUserDao();
    }

    public static UserDao getInstance() {
        return DefaultUserDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public void save(User user) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into user_table(login,password,role) values (?,?,?)")) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByLogin(String login) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from user_table where login =?")) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new User(login, rs.getString("password"), Role.valueOf(rs.getString("role")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        User user;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from user_table where role='USER'")) {
            try {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    user = new User(rs.getString("login"), rs.getString("password"));
                    users.add(user);
                }
                return users;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void deleteUser(String login) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from user_table where login=?")) {
            statement.setString(1, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
