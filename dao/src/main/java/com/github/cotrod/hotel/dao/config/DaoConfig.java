package com.github.cotrod.hotel.dao.config;

import com.github.cotrod.hotel.dao.HotelRoomDao;
import com.github.cotrod.hotel.dao.MealDao;
import com.github.cotrod.hotel.dao.OrderDao;
import com.github.cotrod.hotel.dao.UserDao;
import com.github.cotrod.hotel.dao.impl.DefaultHotelRoomDao;
import com.github.cotrod.hotel.dao.impl.DefaultMealDao;
import com.github.cotrod.hotel.dao.impl.DefaultOrderDao;
import com.github.cotrod.hotel.dao.impl.DefaultUserDao;
import com.github.cotrod.hotel.dao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.github.cotrod.hotel.dao.repository")
@EntityScan("com.github.cotrod.hotel.dao.entity")
public class DaoConfig {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Bean
    public HotelRoomDao hotelRoomDao() {
        return new DefaultHotelRoomDao(hotelRoomRepository);
    }

    @Bean
    public MealDao mealDao() {
        return new DefaultMealDao(mealRepository, orderRepository);
    }

    @Bean
    public OrderDao orderDao() {
        return new DefaultOrderDao(orderRepository, clientRepository, hotelRoomRepository);
    }

    @Bean
    public UserDao userDao() {
        return new DefaultUserDao(userRepository, clientRepository);
    }
}
