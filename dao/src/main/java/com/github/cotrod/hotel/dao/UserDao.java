package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.UserDTO;

import java.util.List;

public interface UserDao {

    long save(UserDTO userDTO);

    UserDTO getUserByLogin(String login);

    UserDTO getUserById(long id);

    List<UserDTO> getUsers();

    void deleteUser(long id);

    void changePassword(long id, String password);
}
