package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public List<OrderAdminDTO> getAdminOrders() {
        List<OrderAdminDTO> orders = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select ord.id, ord.date_in, ord.date_out, us.login, hot.type, hot.amount_of_rooms, ord.decision from order_t ord " +
                             "join hotel_room hot on room_id = hot.id " +
                             "join  user us on client_id=us.id;")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long orderId = rs.getLong("id");
                LocalDate dateIn = rs.getDate("date_in").toLocalDate();
                LocalDate dateOut = rs.getDate("date_out").toLocalDate();
                String login = rs.getString("login");
                RoomType type = RoomType.valueOf(rs.getString("type"));
                int amountOfRooms = rs.getInt("amount_of_rooms");
                Decision decision = Decision.valueOf(rs.getString("decision"));
                OrderAdminDTO orderAdminDTO = new OrderAdminDTO(orderId, dateIn, dateOut, login, type, amountOfRooms, decision);
                orders.add(orderAdminDTO);
            }
            return orders;
        } catch (SQLException e) {
            log.warn("{}", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void updateDecision(long id, Decision decision) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("update order_t set decision = ? where id=?")) {
            statement.setString(1, decision.name());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("{}", e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<OrderUserDTO> getUserOrders(long userId) {
        List<OrderUserDTO> orders = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from order_t join hotel_room on order_t.room_id = hotel_room.id where client_id=?")) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long orderId = rs.getLong("id");
                LocalDate dateIn = rs.getDate("date_in").toLocalDate();
                LocalDate dateOut = rs.getDate("date_out").toLocalDate();
                RoomType type = RoomType.valueOf(rs.getString("type"));
                int amountOfRooms = rs.getInt("amount_of_rooms");
                Decision decision = Decision.valueOf(rs.getString("decision"));
                OrderUserDTO orderDTO = new OrderUserDTO(orderId, dateIn, dateOut, type, amountOfRooms, decision);
                orders.add(orderDTO);
            }
            return orders;
        } catch (SQLException e) {
            log.warn("", e);
            throw new RuntimeException();
        }


    }
}
