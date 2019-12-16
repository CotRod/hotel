package com.github.cotrod.hotel.web.spring;

import com.github.cotrod.hotel.service.config.ServiceConfig;
import com.github.cotrod.hotel.web.controller.AdminController;
import com.github.cotrod.hotel.web.controller.CommonController;
import com.github.cotrod.hotel.web.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebConfig {
    private ServiceConfig serviceConfig;

    public WebConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    // ************* MyBeans *************

    @Bean
    public CommonController commonController() {
        return new CommonController(serviceConfig.userService());
    }

    @Bean
    public UserController userController() {
        return new UserController(serviceConfig.orderService(), serviceConfig.hotelRoomService(), serviceConfig.mealService(), serviceConfig.userService());
    }

    @Bean
    public AdminController adminController() {
        return new AdminController(serviceConfig.userService(), serviceConfig.orderService());
    }

    // ************* Settings *************

    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
        return tilesConfigurer;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:translations");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("ru_RU"));
        resolver.setCookieName("LocaleCookie");
        resolver.setCookieMaxAge(3600);
        return resolver;
    }
}
