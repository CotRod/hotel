package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.User;

public interface UserDao {

    void save(User user);

    User getUserByLogin(String login);
}
