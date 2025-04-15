package com.springrest.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.rest.entity.UserEntity;
import com.springrest.rest.security.SecurityConfig;
import com.springrest.rest.service.UserServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AuthController {
    private final SecurityConfig securityConfig;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserServiceImpl userService, SecurityConfig securityConfig, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.securityConfig = securityConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody String username, String password) {
        logger.info("Received Login request (username: " + username + " ; password: " + password + " )");

        Optional<UserEntity> userOpt = userService.findByUsername(username);
        logger.info(userOpt.toString());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        UserEntity user = userOpt.get();
        logger.info(user.toString());

        if (!securityConfig.passwordEncoder().matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        return ResponseEntity.ok("Login successful!");
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody String username, String password) {
        logger.info("Received Register request (username: " + username + " ; password: " + password + " )");

        // Створення нового користувача
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of("USER"));

        try {
            userService.saveUser(user);
            logger.info("Created User");
            // UserEntity savedUser = userService.saveUser(user);
            // return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            logger.info("Something went wrong");
            // return ResponseEntity.badRequest().body(null);
        }
    }
}