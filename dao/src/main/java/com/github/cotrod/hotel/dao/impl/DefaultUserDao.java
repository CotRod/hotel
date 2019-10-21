package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultUserDao.class);

    private static class SingletonHolder {
        static final UserDao HOLDER_INSTANCE = new DefaultUserDao();
    }

    public static UserDao getInstance() {
        return DefaultUserDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public long save(UserSignupDTO userSignup) {
        Connection connection = null;
        long id;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("insert into client (first_name,last_name) values (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, userSignup.getFirstName());
                statement.setString(2, userSignup.getLastName());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                id = rs.getLong(1);
            }
            try (PreparedStatement statement = connection.prepareStatement("insert into user(id,login,password) values (?,?,?)")) {
                statement.setLong(1, id);
                statement.setString(2, userSignup.getLogin());
                statement.setString(3, userSignup.getPassword());
                statement.executeUpdate();
            }
            connection.commit();
            return id;
        } catch (SQLException e) {
            log.warn("dao.save error {}", userSignup);
            throw new RuntimeException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }


    @Override
    public UserDTO getUserByLogin(String login) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from client join user on client.id = user.id " +
                     "where login =?")) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createUserDTO(rs);
            }

            return null;
        } catch (SQLException e) {
            log.warn("Login {} is not exist", login);
            throw new RuntimeException();
        }
    }

    @Override
    public UserDTO getUserById(long id) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from client join user on client.id = user.id where user.id =?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return createUserDTO(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            log.warn("id {} is not exist", id);
            throw new RuntimeException();
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserDTO> users = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from client join user on client.id = user.id where role='USER'")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(createUserDTO(rs));
            }
            return users;
        } catch (SQLException e) {
            log.warn("There are no users :(");
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteUser(long id) {
        Connection connection = null;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("delete from user where id=?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement("delete from client where id=?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            log.warn("Login {} is not exist", id);
            throw new RuntimeException();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void changePassword(long id, String password) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("update user set password=? where id=?")) {
            statement.setString(1, password);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception in dao.changePassword()");
            throw new RuntimeException();
        }
    }

    private UserDTO createUserDTO(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String login = rs.getString("login");
        String password = rs.getString("password");
        Role role = Role.valueOf(rs.getString("role"));
        String firstName = rs.getString("first_name");
        return new UserDTO(id, login, password, role, firstName);
    }
}
