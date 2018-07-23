package com.codecrete.sqlms.configuration;

import com.codecrete.domain.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 */
@Configuration
public class ModelConfiguration {
    
    private static final Logger LOG = LogManager.getLogger(ModelConfiguration.class);

    @Bean
    @Lazy
    @Scope("prototype")
    public User user(String email) {

        User user = new User();
        user.setEmail(email);
        user.setCreated(LocalDateTime.now(ZoneOffset.UTC));
        user.setModified(LocalDateTime.now(ZoneOffset.UTC));

        return user;
    }
}
