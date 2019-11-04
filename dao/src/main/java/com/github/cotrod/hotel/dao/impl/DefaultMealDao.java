package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.dao.entity.Meal;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.model.TypeOfMeal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DefaultMealDao implements MealDao {
    private static class SingletonHolder {
        static final MealDao HOLDER_INSTANCE = new DefaultMealDao();
    }

    public static MealDao getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public void addMealToOrder(Long orderId, TypeOfMeal mealType) {
        Session session = EMUtil.getEntityManager().getSession();
        Query mealQuery = session.createQuery("from Meal as m where m.typeOfMeal = :type");
        mealQuery.setParameter("type", mealType);
        Meal meal = (Meal) mealQuery.getSingleResult();

        Order order = session.get(Order.class, orderId);

        order.getMeals().add(meal);
        meal.getOrders().add(order);

        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(order);
        transaction.commit();
        session.close();
    }
}
