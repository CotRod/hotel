package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.DataSource;
import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.model.*;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.github.cotrod.hotel.model.Decision.AWAITING;

public class DefaultOrderDao implements OrderDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultOrderDao.class);

    private static class SingletonHolder {
        static final OrderDao HOLDER_INSTANCE = new DefaultOrderDao();
    }

    public static OrderDao getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long makeOrder(OrderDTO orderDTO) {
        Session session = EMUtil.getEntityManager().getSession();
        session.beginTransaction();
        Order order = new Order();
        order.setDateIn(orderDTO.getDateIn());
        order.setDateOut(orderDTO.getDateOut());
        order.setDecision(AWAITING);
        Client client = session.get(Client.class, orderDTO.getClientId());
        HotelRoom hotelRoom = session.get(HotelRoom.class, orderDTO.getRoomId());
        int quantity = hotelRoom.getQuantity();
        hotelRoom.setQuantity(--quantity);
        order.setClient(client);
        order.setHotelRoom(hotelRoom);
        hotelRoom.setOrder(order);
        client.getOrder().add(order);
        session.save(order);
        session.saveOrUpdate(hotelRoom);
        session.getTransaction().commit();
        session.close();
        return order.getId();
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
            log.warn("", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void updateDecision(Long id, Decision decision) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("update order_t set decision = ? where id=?")) {
            statement.setString(1, decision.name());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("", e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<OrderUserDTO> getUserOrders(Long userId) {
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
