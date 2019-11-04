package com.github.cotrod.hotel.dao.impl;

import com.github.cotrod.hotel.dao.EMUtil;
import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.dao.entity.Meal;
import com.github.cotrod.hotel.dao.entity.Order;
import com.github.cotrod.hotel.model.TypeOfMeal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.RollbackException;

public class DefaultMealDao implements MealDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultMealDao.class);
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

        try {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(order);
            transaction.commit();
        } catch (RollbackException e) {
            log.warn("", e);
        } finally {
            session.close();
        }
    }
}
