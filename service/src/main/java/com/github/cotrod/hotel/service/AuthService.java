package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.User;

import java.util.List;

public interface AuthService {
    User getUser(String login, String password);
    User saveUser(String login, String password);
    List<User> getUsers();
    void deleteUser(String login);
}
