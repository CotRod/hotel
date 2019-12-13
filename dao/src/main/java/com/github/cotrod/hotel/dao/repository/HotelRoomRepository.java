package com.github.cotrod.hotel.dao.repository;

import com.github.cotrod.hotel.dao.entity.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
}
