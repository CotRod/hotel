package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.dao.converter.OrderConverter;
import com.github.cotrod.hotel.dao.entity.Client;
import com.github.cotrod.hotel.dao.entity.HotelRoom;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.dao.repository.ClientRepository;
import com.github.cotrod.hotel.dao.repository.HotelRoomRepository;
import com.github.cotrod.hotel.dao.repository.OrderRepository;
import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderCreateDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.cotrod.hotel.dao.converter.OrderConverter.toEntity;


public class DefaultOrderDao implements OrderDao {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final HotelRoomRepository roomRepository;

    public DefaultOrderDao(OrderRepository orderRepository, ClientRepository clientRepository, HotelRoomRepository roomRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Long makeOrder(OrderCreateDTO orderDTO) {
        Order order = toEntity(orderDTO);
        Client client = clientRepository.getOne(orderDTO.getClientId());
        HotelRoom hotelRoom = roomRepository.getOne(orderDTO.getRoomId());
        int quantity = hotelRoom.getQuantity();
        hotelRoom.setQuantity(--quantity);
        order.setClient(client);
        client.getOrder().add(order);
        order.setHotelRoom(hotelRoom);
        hotelRoom.getOrder().add(order);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public List<OrderDTO> getOrders(Long userId, int page) {
        List<OrderDTO> orders = new ArrayList<>();
        Page<Order> ordersPage = getPage(userId, page);
        ordersPage.get().forEach(order -> orders.add(OrderConverter.fromEntity(order)));
        return orders;
    }

    @Override
    public boolean isLastPage(Long userId, int page) {
        Page<Order> orderPage = getPage(userId, page);
        return orderPage.isLast();
    }

    private Page<Order> getPage(Long userId, int page) {
        ResourceBundle resource = ResourceBundle.getBundle("const");
        int amountOfOrdersOnPage = Integer.parseInt(resource.getString("amountOfOrdersOnPage"));
        Page<Order> ordersPage;
        if (userId == 0) {
            ordersPage = orderRepository.findAll(PageRequest.of(page, amountOfOrdersOnPage));
        } else {
            Client clientExample = new Client();
            clientExample.setId(userId);
            Order orderExample = new Order();
            orderExample.setClient(clientExample);
            Example<Order> example = Example.of(orderExample);
            ordersPage = orderRepository.findAll(example, PageRequest.of(page, amountOfOrdersOnPage));
        }
        return ordersPage;
    }

    @Override
    public void updateDecision(Long id, Decision decision) {
        Order order = orderRepository.getOne(id);
        order.setDecision(decision);
        orderRepository.save(order);
    }
}
