package com.expensetrackerdemo.demo.security;

import com.expensetrackerdemo.demo.entity.User;
import com.expensetrackerdemo.demo.repos.RepoUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final RepoUser repoUser;

    public CustomUserDetailsService(RepoUser repoUser) {
        this.repoUser = repoUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repoUser.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
