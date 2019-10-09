package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    User getUserByLogin(String login);

    List<User> getUsers();

    void deleteUser(String login);

    void changePassword(String login, String password);
}
