package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;

import java.util.List;

public interface UserDao {

    Long save(UserSignupDTO userSignup);

    UserDTO getUserByLogin(String login);

    UserDTO getUserById(Long id);

    List<UserDTO> getUsers(Role role);

    void deleteUser(Long id);

    void changePassword(Long id, String password);
}
