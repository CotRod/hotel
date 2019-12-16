package com.github.cotrod.hotel;

import com.github.cotrod.hotel.dao.config.DaoConfig;
import com.github.cotrod.hotel.service.config.ServiceConfig;
import com.github.cotrod.hotel.web.spring.RootConfig;
import com.github.cotrod.hotel.web.spring.WebConfig;
import com.github.cotrod.hotel.web.spring.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({DaoConfig.class, ServiceConfig.class, WebConfig.class, WebSecurityConfig.class, RootConfig.class})
public class AppRunner {
    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
