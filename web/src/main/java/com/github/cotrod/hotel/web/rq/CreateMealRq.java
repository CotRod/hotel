package com.github.cotrod.hotel.web.rq;

public class CreateMealRq {
    private String[] meals;

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String[] getMeals() {
        return meals;
    }

    public void setMeals(String[] meals) {
        this.meals = meals;
    }
}
