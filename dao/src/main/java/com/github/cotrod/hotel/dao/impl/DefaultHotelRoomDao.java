package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.model.HotelRoom;
import com.github.cotrod.hotel.model.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<HotelRoom> getRooms() {
        List<HotelRoom> rooms = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from hotel_room where Quantity>0")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rooms.add(createHotelRoom(rs));
            }
            return rooms;
        } catch (SQLException e) {
            log.warn("Sql exception {}", e);
            throw new RuntimeException();
        }
    }

    private HotelRoom createHotelRoom(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        RoomType type = RoomType.valueOf(rs.getString("type"));
        int amountOfRooms = rs.getInt("amount_of_rooms");
        int quantity = rs.getInt("quantity");
        return new HotelRoom(id, type, amountOfRooms, quantity);
    }
}
