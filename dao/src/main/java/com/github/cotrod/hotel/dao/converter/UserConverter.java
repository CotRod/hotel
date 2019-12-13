package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;

public class UserConverter {
    public static UserDTO fromEntity(User userFromDB) {
        if (userFromDB == null) {
            return null;
        }
        long id = userFromDB.getClient().getId();
        String login = userFromDB.getLogin();
        String password = userFromDB.getPassword();
        Role role = userFromDB.getRole();
        String firstName = userFromDB.getClient().getFirstName();
        return new UserDTO(id, login, password, role, firstName);
    }

    public static User toEntity(UserSignupDTO userSignupDTO) {
        if (userSignupDTO == null) {
            return null;
        }
        User user = new User();
        user.setLogin(userSignupDTO.getLogin());
        user.setPassword(userSignupDTO.getPassword());
        user.setRole(userSignupDTO.getRole());
        return user;
    }
}
