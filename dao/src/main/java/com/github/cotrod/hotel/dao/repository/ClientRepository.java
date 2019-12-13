package com.github.cotrod.hotel.dao.repository;

import com.github.cotrod.hotel.dao.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
