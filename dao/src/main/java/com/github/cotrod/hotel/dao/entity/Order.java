package com.github.cotrod.hotel.dao.entity;

import com.github.cotrod.hotel.model.Decision;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "order_t")
public class Order implements Serializable {
    private Long id;
    //    private Long clientId;
//    private Long roomId;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private Decision decision;
    private Client client;
    private HotelRoom hotelRoom;

    public Order() {
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

//    @Id
//    @Column(name = "client_id")
//    public Long getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(Long clientId) {
//        this.clientId = clientId;
//    }
//
//    @Id
//    @Column(name = "room_id")
//    public Long getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(Long roomId) {
//        this.roomId = roomId;
//    }

    @Column(name = "date_in")
    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    @Column(name = "date_out")
    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "decision")
    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    @ManyToOne
//    @MapsId
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @OneToOne
//    @MapsId
    @JoinColumn(name = "room_id")
    public HotelRoom getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(HotelRoom hotelRoom) {
        this.hotelRoom = hotelRoom;
    }
}
