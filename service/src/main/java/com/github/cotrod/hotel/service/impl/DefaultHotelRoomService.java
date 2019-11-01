package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.impl.DefaultHotelRoomDao;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.service.HotelRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultHotelRoomService implements HotelRoomService {
    private static final Logger log = LoggerFactory.getLogger(DefaultHotelRoomService.class);
    private HotelRoomDao hotelRoomDao = DefaultHotelRoomDao.getInstance();

    private static class SingletonHolder {
        static final HotelRoomService HOLDER_INSTANCE = new DefaultHotelRoomService();
    }

    public static HotelRoomService getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<HotelRoomDTO> getRooms() {
        return hotelRoomDao.getRooms();
    }
}
