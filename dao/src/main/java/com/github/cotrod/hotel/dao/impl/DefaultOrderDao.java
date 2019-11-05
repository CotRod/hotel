package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.github.cotrod.hotel.dao.converter.ConverterToDTO.createOrderDTO;
import static com.github.cotrod.hotel.dao.converter.ConverterToEntity.createOrderEntity;

public class DefaultOrderDao implements OrderDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultOrderDao.class);

    private static class SingletonHolder {
        static final OrderDao HOLDER_INSTANCE = new DefaultOrderDao();
    }

    public static OrderDao getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long makeOrder(OrderCreateDTO orderDTO) {
        try (Session session = EMUtil.getEntityManager().getSession()) {
            Order order = createOrderEntity(orderDTO);
            session.beginTransaction();
            Client client = session.get(Client.class, orderDTO.getClientId());
            HotelRoom hotelRoom = session.get(HotelRoom.class, orderDTO.getRoomId());
            int quantity = hotelRoom.getQuantity();
            hotelRoom.setQuantity(--quantity);
            order.setClient(client);
            order.setHotelRoom(hotelRoom);
            hotelRoom.getOrder().add(order);
            client.getOrder().add(order);
            session.save(order);
            session.saveOrUpdate(hotelRoom);
            session.getTransaction().commit();
            return order.getId();
        } catch (RollbackException e) {
            log.error("orderDTO: {}", orderDTO, e);
            return -1L;
        }
    }

    @Override
    public List<OrderDTO> getOrders(Long userId, int firstResult, int maxResult) {
        List<OrderDTO> orders = new ArrayList<>();
        CriteriaBuilder cb = EMUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);
        if (userId == 0) {
            criteria.select(orderRoot);
        } else {
            criteria.select(orderRoot).where(cb.equal(orderRoot.get("client"), userId));
        }
        TypedQuery<Order> typedQuery = EMUtil.getEntityManager().createQuery(criteria);
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(maxResult);
        List<Order> ordersFromDB = typedQuery.getResultList();
        if (ordersFromDB.size() > 0) {
            ordersFromDB.forEach(order -> orders.add(createOrderDTO(order)));
            return orders;
        }
        return null;
    }

    @Override
    public void updateDecision(Long id, Decision decision) {
        Session session = EMUtil.getEntityManager().getSession();
        try {
            Transaction tx = session.beginTransaction();
            Order order = session.get(Order.class, id);
            order.setDecision(decision);
            tx.commit();
        } catch (RollbackException e) {
            log.warn("id: {}, decision: {} ", id, decision, e);
        } finally {
            session.close();
        }
    }

    @Override
    public long getAmountOfOrders(Long userId) {
        CriteriaBuilder cb = EMUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<Order> orderRoot = criteria.from(Order.class);
        if (userId == 0) {
            criteria.select(cb.count(orderRoot));
        } else {
            criteria.select(cb.count(orderRoot)).where(cb.equal(orderRoot.get("client"), userId));
        }
        try {
            return EMUtil.getEntityManager().createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}
