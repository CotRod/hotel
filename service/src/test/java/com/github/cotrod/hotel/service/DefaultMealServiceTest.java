package com.github.cotrod.hotel.service;

import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.model.TypeOfMeal;
import com.github.cotrod.hotel.service.impl.DefaultMealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class DefaultMealServiceTest {
    @Mock
    MealDao mealDao;
    @InjectMocks
    DefaultMealService service;

    @Test
    void addMealToOrderTest() {
        doNothing().when(mealDao).addMealToOrder(1L, TypeOfMeal.BREAKFAST);
        service.addMealToOrder(1L, "BREAKFAST");
    }
}