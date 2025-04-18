package com.springrest.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springrest.rest.entity.UserEntity;
import com.springrest.rest.exception.InvalidLoginException;
import com.springrest.rest.security.SecurityConfig;
import com.springrest.rest.service.UserServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
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

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        logger.info("Received Login request (username: " + username + " ; password: " + password + " )");

        Optional<UserEntity> userOpt = userService.findByUsername(username);

        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/login";
        }

        UserEntity user = userOpt.get();

        if (!securityConfig.passwordEncoder().matches(password, user.getPassword())) {
            throw new InvalidLoginException("Invalid username or password");
        }

        // 🔐 Завантажуємо UserDetails через сервіс
        UserDetails userDetails = userService.loadUserByUsername(username);

        // ✅ Створюємо токен автентифікації
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        // ⬇️ Встановлюємо в контекст безпеки
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 🗂️ Зберігаємо контекст в сесії, щоб він не зникав після редіректу
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        logger.info("User " + username + " authenticated and session created.");

        if (user.getRoles().contains("ADMIN")) {
            return "redirect:/userpage";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody String username, String password) {
        logger.info("Received Register request (username: " + username + " ; password: " + password + " )");

        // Створення нового користувача
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of("USER"));

        try {
            userService.saveUser(user);
            logger.info("Created User");
            return ResponseEntity.status(302).header("Location", "/login").build();
        } catch (RuntimeException e) {
            logger.info("Something went wrong");
            return ResponseEntity.badRequest().build();
        }
    }
}