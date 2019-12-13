package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.model.TypeOfMeal;
import com.github.cotrod.hotel.service.MealService;
import org.springframework.transaction.annotation.Transactional;

public class DefaultMealService implements MealService {
    private MealDao mealDao;

    public DefaultMealService(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    @Transactional
    public void addMealToOrder(Long orderId, String meal) {
        TypeOfMeal mealType = TypeOfMeal.valueOf(meal);
        mealDao.addMealToOrder(orderId, mealType);
    }
}
