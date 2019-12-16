package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.dao.config.DaoConfig;
import com.github.cotrod.hotel.dao.entity.Meal;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.dao.repository.MealRepository;
import com.github.cotrod.hotel.dao.repository.OrderRepository;
import com.github.cotrod.hotel.model.TypeOfMeal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
class DefaultMealDaoTest {
    @Autowired
    MealRepository mealRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MealDao dao;

    @Test
    void addMealToOrder() {
        Meal meal = new Meal();
        meal.setTypeOfMeal(TypeOfMeal.BREAKFAST);
        mealRepository.save(meal);
        Order order = new Order();
        orderRepository.save(order);
        dao.addMealToOrder(1L, TypeOfMeal.BREAKFAST);
        assertEquals(1, orderRepository.getOne(1L).getMeals().size());
    }
}