package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.model.ChangePassDTO;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserLoginDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;

import java.util.List;

public interface UserService {
    UserDTO getUser(UserLoginDTO userLogin);

    UserDTO getUserByLogin(String login);

    UserDTO saveUser(UserSignupDTO userSignup);

    List<UserDTO> getUsers();

    void deleteUser(long id);

    boolean changePassword(long id, ChangePassDTO changePassDTO);
}
