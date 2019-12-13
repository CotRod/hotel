package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.converter.ClientConverter;
import com.github.cotrod.hotel.dao.converter.UserConverter;
import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.dao.repository.ClientRepository;
import com.github.cotrod.hotel.dao.repository.UserRepository;
import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DefaultUserDao implements UserDao {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public DefaultUserDao(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Long save(UserSignupDTO userSignup) {
        Client client = ClientConverter.toEntity(userSignup);
        User user = UserConverter.toEntity(userSignup);
        client.setUser(user);
        user.setClient(client);
        userRepository.save(user);
        return client.getId();
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        User user = userRepository.getByLogin(login);
        return UserConverter.fromEntity(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return UserConverter.fromEntity(user);
        }
        return null;
    }

    @Override
    public List<UserDTO> getUsers(Role role) {
        List<UserDTO> users = new ArrayList<>();
        List<User> usersFromDB = userRepository.getAllByRole(role);
        if (usersFromDB.size() > 0) {
            usersFromDB.forEach(user -> users.add(UserConverter.fromEntity(user)));
        }
        return users;
    }

    @Override
    public void deleteUser(Long id) {
        Client client = clientRepository.getOne(id);
        clientRepository.delete(client);
    }

    @Override
    public void changePassword(Long id, String password) {
        User user = userRepository.getOne(id);
        user.setPassword(password);
        userRepository.save(user);
    }
}
