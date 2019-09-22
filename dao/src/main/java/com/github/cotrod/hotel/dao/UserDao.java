package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    void save(User user);

    User getUserByLogin(String login);
}
