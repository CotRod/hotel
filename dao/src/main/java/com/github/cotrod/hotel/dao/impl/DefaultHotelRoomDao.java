package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.converter.HotelRoomConverter;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.dao.repository.HotelRoomRepository;
import com.github.cotrod.hotel.model.HotelRoomDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultHotelRoomDao implements HotelRoomDao {
    private final HotelRoomRepository repository;

    public DefaultHotelRoomDao(HotelRoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HotelRoomDTO> getRooms() {
        List<HotelRoom> hotelRooms = repository.findAll();
        return hotelRooms.stream()
                .map(HotelRoomConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public HotelRoomDTO getRoomById(Long id) {
        Optional<HotelRoom> roomOptional = repository.findById(id);
        if (roomOptional.isPresent()) {
            HotelRoom hotelRoom = roomOptional.get();
            return HotelRoomConverter.fromEntity(hotelRoom);
        } else
            return null;
    }


}
