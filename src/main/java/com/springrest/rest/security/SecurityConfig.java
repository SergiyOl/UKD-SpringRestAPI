package com.springrest.rest.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.springrest.rest.entity.UserEntity;
import com.springrest.rest.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity(prePostEnabled = true, securedEnabled = true,
// jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private final UserDetailsService userService;

    public SecurityConfig(UserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
    // {
    // UserDetails admin = User.builder()
    // .username("admin")
    // .password(passwordEncoder.encode("admin123"))
    // .roles("USER", "ADMIN")
    // .build();

    // UserDetails user = User.builder()
    // .username("user")
    // .password(passwordEncoder.encode("user123"))
    // .roles("USER")
    // .build();

    // return new InMemoryUserDetailsManager(admin, user);
    // }

    @Bean
    public CommandLineRunner initAdmin(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userService.findByUsername("admin").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // 🔒 Захищений пароль
                admin.setRoles(Set.of("ADMIN", "USER"));
                userService.saveUser(admin);
                System.out.println("✅ Created default admin user (admin/admin123)");
            } else {
                System.out.println("ℹ️ Admin user already exists.");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/userpage").authenticated()
                        .requestMatchers("/studentslist").hasRole("USER")
                        .requestMatchers("/form").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll());

        return http.build();
    }
}