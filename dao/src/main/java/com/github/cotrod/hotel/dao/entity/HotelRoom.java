package com.github.cotrod.hotel.dao.entity;

import com.github.cotrod.hotel.model.RoomType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel_room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HotelRoom {
    private Long id;
    private RoomType type;
    private int amountOfRooms;
    private int quantity;
    private List<Order> orders = new ArrayList<>();

    public HotelRoom() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    @Column(name = "amount_of_rooms")
    public int getAmountOfRooms() {
        return amountOfRooms;
    }

    public void setAmountOfRooms(int amountOfRooms) {
        this.amountOfRooms = amountOfRooms;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @OneToMany(mappedBy = "hotelRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Order> getOrder() {
        return orders;
    }

    public void setOrder(List<Order> orders) {
        this.orders = orders;
    }
}
