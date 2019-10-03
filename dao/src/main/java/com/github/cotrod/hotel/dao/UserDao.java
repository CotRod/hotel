package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {

    void save(User user);

    Connection connect() throws SQLException;

    User getUserByLogin(String login);
}
