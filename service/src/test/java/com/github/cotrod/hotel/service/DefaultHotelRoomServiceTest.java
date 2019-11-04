package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.service.impl.DefaultHotelRoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultHotelRoomServiceTest {
    @Mock
    HotelRoomDao roomDao;
    @InjectMocks
    DefaultHotelRoomService service;

    @Test
    void getRooms() {
        when(roomDao.getRooms()).thenReturn(new ArrayList<HotelRoomDTO>());
        assertNotNull(service.getRooms());
    }
}