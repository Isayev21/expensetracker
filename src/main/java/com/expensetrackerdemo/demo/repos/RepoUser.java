package com.expensetrackerdemo.demo.repos;

import com.expensetrackerdemo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoUser extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

}
