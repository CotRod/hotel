package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultUserDao.class);

    private static class SingletonHolder {
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
            log.warn("dao.save error {}", user);
            throw new RuntimeException();
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
            return null;
        } catch (SQLException e) {
            log.warn("Login {} is not exist", login);
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        User user;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from user_table where role='USER'")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("login"), rs.getString("password"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            log.warn("There are no users :(");
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteUser(String login) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from user_table where login=?")) {
            statement.setString(1, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Login {} is not exist", login);
            throw new RuntimeException();
        }
    }

    @Override
    public void changePassword(String login, String password) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("update user_table set password=? where login=?")) {
            statement.setString(1, password);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception in dao.changePassword()");
            throw new RuntimeException();
        }
    }
}
