package com.expensetrackerdemo.demo.security;

import com.expensetrackerdemo.demo.entity.User;
import com.expensetrackerdemo.demo.repos.RepoUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(RepoUser repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUserName("ali").isPresent()) {
                User user = new User();
                user.setUserName("ali");
                user.setPassword(encoder.encode("123456"));
                user.setCreatedDate(LocalDateTime.now());

                repo.save(user);

                System.out.println("Test user 'john' created.");
            }
        };
    }
}
