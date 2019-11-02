package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.RoomType;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DefaultHotelRoomDao implements HotelRoomDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultHotelRoomDao.class);

    private static class SingletonHolder {
        static final HotelRoomDao HOLDER_INSTANCE = new DefaultHotelRoomDao();
    }

    public static HotelRoomDao getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<HotelRoomDTO> getRooms() {
        List<HotelRoomDTO> rooms = new ArrayList<>();
        CriteriaBuilder cb = EMUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HotelRoom> criteria = cb.createQuery(HotelRoom.class);
        Root<HotelRoom> roomRoot = criteria.from(HotelRoom.class);
        criteria.select(roomRoot);
        List<HotelRoom> roomsFromDB = EMUtil.getEntityManager().createQuery(criteria).getResultList();
        if (roomsFromDB.size() > 0) {
            roomsFromDB.forEach(room -> rooms.add(createHotelRoom((HotelRoom) room)));
            return rooms;
        }
        return null;
    }

    @Override
    public HotelRoomDTO getRoomById(Long id) {
        Session session = EMUtil.getEntityManager().getSession();
        HotelRoom roomFromDB = session.get(HotelRoom.class, id);
        session.close();
        if (roomFromDB != null) {
            return createHotelRoom(roomFromDB);
        }
        return null;
    }

    private HotelRoomDTO createHotelRoom(HotelRoom roomFromDB) {
        Long id = roomFromDB.getId();
        RoomType type = roomFromDB.getType();
        int amountOfRooms = roomFromDB.getAmountOfRooms();
        int quantity = roomFromDB.getQuantity();
        return new HotelRoomDTO(id, type, amountOfRooms, quantity);
    }
}
