package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
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
    public long save(UserDTO userDTO) {
        Connection connection = null;
        long id;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("insert into client (first_name,last_name) values (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, userDTO.getFirstName());
                statement.setString(2, userDTO.getLastName());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                id = rs.getLong(1);
                userDTO.setId(id);
            }
            try (PreparedStatement statement = connection.prepareStatement("insert into user(id,login,password) values (?,?,?)")) {
                statement.setLong(1, id);
                statement.setString(2, userDTO.getLogin());
                statement.setString(3, userDTO.getPassword());
                statement.executeUpdate();
            }

            connection.commit();
            return id;
        } catch (SQLException e) {
            log.warn("dao.save error {}", userDTO);
            throw new RuntimeException();
        }
    }


    @Override
    public UserDTO getUserByLogin(String login) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from client join user on client.id = user.id " +
                     "where login =?")) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return CreateUserDTO(rs);
                }
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
                    return CreateUserDTO(rs);
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
        UserDTO userDTO;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from client join user on client.id = user.id where role='USER'")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                userDTO = CreateUserDTO(rs);
                users.add(userDTO);
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

    private UserDTO CreateUserDTO(ResultSet rs) throws SQLException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(rs.getLong("id"));
        userDTO.setFirstName(rs.getString("first_name"));
        userDTO.setLogin(rs.getString("login"));
        userDTO.setPassword(rs.getString("password"));
        userDTO.setRole(Role.valueOf(rs.getString("role")));
        return userDTO;
    }
}
