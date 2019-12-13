package com.github.cotrod.hotel.dao.repository;

import com.github.cotrod.hotel.dao.entity.User;
import com.github.cotrod.hotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByLogin(String login);

    List<User> getAllByRole(Role role);
}
