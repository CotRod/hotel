package com.github.cotrod.hotel.web.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user**").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/logout", "/home").hasAnyRole()
                .antMatchers("/login", "/signup", "/").permitAll()
                .anyRequest().authenticated();

    }
}
