package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.dao.entity.Meal;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.dao.repository.MealRepository;
import com.github.cotrod.hotel.dao.repository.OrderRepository;
import com.github.cotrod.hotel.model.TypeOfMeal;

public class DefaultMealDao implements MealDao {
    private final MealRepository mealRepository;
    private final OrderRepository orderRepository;

    public DefaultMealDao(MealRepository mealRepository, OrderRepository orderRepository) {
        this.mealRepository = mealRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addMealToOrder(Long orderId, TypeOfMeal mealType) {
        Meal meal = mealRepository.getMealByTypeOfMeal(mealType);
        Order order = orderRepository.getOne(orderId);
        order.getMeals().add(meal);
        meal.getOrders().add(order);
        orderRepository.save(order);
        mealRepository.save(meal);
    }
}
