package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.service.HotelRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultHotelRoomService implements HotelRoomService {
    private static final Logger log = LoggerFactory.getLogger(DefaultHotelRoomService.class);
    private HotelRoomDao hotelRoomDao;

    public DefaultHotelRoomService(HotelRoomDao hotelRoomDao) {
        this.hotelRoomDao = hotelRoomDao;
    }

    @Override
    @Transactional
    public List<HotelRoomDTO> getRooms() {
        return hotelRoomDao.getRooms();
    }
}
