package com.expensetrackerdemo.demo;

import com.expensetrackerdemo.demo.entity.User;
import com.expensetrackerdemo.demo.repos.RepoUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner init(RepoUser repo, PasswordEncoder encoder) {
//		return args -> {
//			if (repo.findByUserName("test2").isEmpty()) {
//				User user = new User();
//				user.setUserName("test2");
//				user.setPassword(encoder.encode("MyUltraSecureJwtSecretKey_2025$$"));
//				user.setCreatedDate(LocalDateTime.now());
//
//				repo.save(user);
//
//				System.out.println("Test user 'test2' created.");
//			}
//		};
//	}
}
