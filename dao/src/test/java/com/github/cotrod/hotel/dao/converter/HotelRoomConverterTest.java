package com.github.cotrod.hotel.dao.converter;

import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.RoomType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelRoomConverterTest {

    @Test
    void fromEntity() {
        HotelRoomDTO NullHotelRoomDTO = HotelRoomConverter.fromEntity(null);
        assertNull(NullHotelRoomDTO);
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setId(1L);
        hotelRoom.setAmountOfRooms(2);
        hotelRoom.setQuantity(1);
        hotelRoom.setType(RoomType.STANDARD);
        HotelRoomDTO hotelRoomDTONotNull = HotelRoomConverter.fromEntity(hotelRoom);
        assertNotNull(hotelRoomDTONotNull);
        assertEquals(hotelRoom.getId(), hotelRoomDTONotNull.getId());
        assertEquals(hotelRoom.getAmountOfRooms(), hotelRoomDTONotNull.getAmountOfRooms());
        assertEquals(hotelRoom.getQuantity(), hotelRoomDTONotNull.getQuantity());
        assertEquals(hotelRoom.getType(), hotelRoomDTONotNull.getType());
    }
}