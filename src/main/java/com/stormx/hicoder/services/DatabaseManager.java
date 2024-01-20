package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository){
        return args -> {
            logger.info("Preloading ");
        };
    }
}
