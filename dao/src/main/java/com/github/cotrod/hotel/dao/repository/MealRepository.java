package com.github.cotrod.hotel.dao.repository;

import com.github.cotrod.hotel.dao.entity.Meal;
import com.github.cotrod.hotel.model.TypeOfMeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal getMealByTypeOfMeal(TypeOfMeal typeOfMeal);

    Meal getByTypeOfMeal(TypeOfMeal typeOfMeal);
}
