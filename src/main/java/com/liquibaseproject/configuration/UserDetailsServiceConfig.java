package com.liquibaseproject.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceConfig {

    @Value("${user.password}")
    private String userPassword;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{noop}" + userPassword).roles("USER").build());
        manager.createUser(User.withUsername("admin").password("{noop}" + adminPassword).roles("ADMIN").build());
        return manager;
    }
}