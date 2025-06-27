package com.expensetrackerdemo.demo.security;

import com.expensetrackerdemo.demo.entity.User;
import com.expensetrackerdemo.demo.repos.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    RepoUser repoUser;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userService;

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, CustomUserDetailsService userService, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (repoUser.findByUserName(user.getUserName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("User already exists");
        }

        // Password encoding
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Optionally set the creation date
        user.setCreatedDate(LocalDateTime.now());

        // Save the user
        repoUser.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println("LOGIN attempt with: " + authRequest.getUserName());

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUserName(),
                            authRequest.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(authRequest.getUserName());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            System.out.println("BAD CREDENTIALS");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            System.out.println("LOGIN ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login");
        }
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> testProtected(){
        return ResponseEntity.ok("You are authorized");
    }
}
