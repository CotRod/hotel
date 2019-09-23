package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.User;

public interface AuthService {
    boolean isValidUser(String login, String password);

    User getUserByLogin(String login);

    void saveUser(String login, String password);
}
