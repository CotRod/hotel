package com.github.cotrod.hotel.service.impl;

import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.dao.impl.DefaultMealDao;
import com.github.cotrod.hotel.model.TypeOfMeal;
import com.github.cotrod.hotel.service.MealService;

public class DefaultMealService implements MealService {
    private MealDao mealDao = DefaultMealDao.getInstance();

    private static class SingletonHolder {
        static final MealService HOLDER_INSTANCE = new DefaultMealService();
    }

    public static MealService getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public void addMealToOrder(Long orderId, String meal) {
        TypeOfMeal mealType = TypeOfMeal.valueOf(meal);
        mealDao.addMealToOrder(orderId, mealType);
    }
}
