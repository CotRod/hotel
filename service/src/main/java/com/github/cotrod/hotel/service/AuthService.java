package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.User;

public interface AuthService {
    User getUser(String login, String password);
    User saveUser(String login, String password);
}
