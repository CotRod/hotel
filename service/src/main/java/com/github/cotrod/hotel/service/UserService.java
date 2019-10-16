package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.ChangePassDTO;
import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUser(User user);

    UserDTO getUserByLogin(String login);

    UserDTO saveUser(UserDTO userDTO);

    List<UserDTO> getUsers();

    void deleteUser(long id);

    boolean changePassword(long id, ChangePassDTO changePassDTO);
}
