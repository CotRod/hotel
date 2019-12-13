package com.github.cotrod.hotel.service.config;

import com.github.cotrod.hotel.dao.config.DaoConfig;
import com.github.cotrod.hotel.service.HotelRoomService;
import com.github.cotrod.hotel.service.MealService;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.service.impl.DefaultHotelRoomService;
import com.github.cotrod.hotel.service.impl.DefaultMealService;
import com.github.cotrod.hotel.service.impl.DefaultOrderService;
import com.github.cotrod.hotel.service.impl.DefaultUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    private DaoConfig daoConfig;

    public ServiceConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    @Bean
    public HotelRoomService hotelRoomService() {
        return new DefaultHotelRoomService(daoConfig.hotelRoomDao());
    }

    @Bean
    public MealService mealService() {
        return new DefaultMealService(daoConfig.mealDao());
    }

    @Bean
    public OrderService orderService() {
        return new DefaultOrderService(daoConfig.orderDao(), daoConfig.hotelRoomDao());
    }

    @Bean
    public UserService userService() {
        return new DefaultUserService(daoConfig.userDao());
    }
}
