package com.github.cotrod.hotel.dao.entity;

import com.github.cotrod.hotel.model.TypeOfMeal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meal")
public class Meal {
    private Long id;
    private TypeOfMeal typeOfMeal;
    private List<Order> orders = new ArrayList<>();

    public Meal() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public TypeOfMeal getTypeOfMeal() {
        return typeOfMeal;
    }

    public void setTypeOfMeal(TypeOfMeal typeOfMeal) {
        this.typeOfMeal = typeOfMeal;
    }

    @ManyToMany
    @JoinTable(name = "order_meal", joinColumns = {@JoinColumn(name = "meal_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")})
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
