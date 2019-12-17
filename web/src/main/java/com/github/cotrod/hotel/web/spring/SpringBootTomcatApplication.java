package com.github.cotrod.hotel.web.spring;

import com.github.cotrod.hotel.dao.config.DaoConfig;
import com.github.cotrod.hotel.service.config.ServiceConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DaoConfig.class, ServiceConfig.class, WebConfig.class, WebSecurityConfig.class, RootConfig.class})
public class SpringBootTomcatApplication extends SpringBootServletInitializer {
}
