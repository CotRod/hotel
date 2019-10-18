package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DefaultOrderDao implements OrderDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultOrderDao.class);

    private static class SingletonHolder {
        static final OrderDao HOLDER_INSTANCE = new DefaultOrderDao();
    }

    public static OrderDao getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public long makeOrder(Order order) {
        Connection connection = null;
        long id;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO order_t(client_id, room_id, date_in, date_out) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, order.getClientId());
                statement.setLong(2, order.getRoomId());
                statement.setDate(3, Date.valueOf(order.getDateIn()));
                statement.setDate(4, Date.valueOf(order.getDateOut()));
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                id = rs.getLong(1);
            }
            try (PreparedStatement statement = connection.prepareStatement("UPDATE hotel_room set quantity=quantity-1 where id=?")) {
                statement.setLong(1, order.getRoomId());
                statement.executeUpdate();
            }
            connection.commit();
            return id;
        } catch (SQLException e) {
            log.warn("{}", e);
            throw new RuntimeException();
        }
    }
}
