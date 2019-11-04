package com.github.cotrod.hotel.dao;

import com.github.cotrod.hotel.model.TypeOfMeal;

public interface MealDao {
    void addMealToOrder(Long orderId, TypeOfMeal mealType);
}
