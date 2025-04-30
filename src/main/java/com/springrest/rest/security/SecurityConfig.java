package com.springrest.rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springrest.rest.jwt.AuthEntryPointJwt;
import com.springrest.rest.jwt.AuthTokenFilter;
import com.springrest.rest.jwt.JwtUtils;
import com.springrest.rest.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthTokenFilter authTokenFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/auth/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();

        return http.build();
    }
}

// @Configuration
// @EnableWebSecurity
// // @EnableMethodSecurity(prePostEnabled = true, securedEnabled = true,
// // jsr250Enabled = true)
// public class SecurityConfig {

// @Autowired
// private final UserDetailsService userService;

// public SecurityConfig(UserDetailsService userService) {
// this.userService = userService;
// }

// @Bean
// public AuthenticationManager authenticationManager() {
// DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
// authProvider.setUserDetailsService(userService);
// authProvider.setPasswordEncoder(passwordEncoder());
// return new ProviderManager(authProvider);
// }

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// // @Bean
// // public UserDetailsService userDetailsService(PasswordEncoder
// passwordEncoder)
// // {
// // UserDetails admin = User.builder()
// // .username("admin")
// // .password(passwordEncoder.encode("admin123"))
// // .roles("USER", "ADMIN")
// // .build();

// // UserDetails user = User.builder()
// // .username("user")
// // .password(passwordEncoder.encode("user123"))
// // .roles("USER")
// // .build();

// // return new InMemoryUserDetailsManager(admin, user);
// // }

// // @Bean
// // public CommandLineRunner initAdmin(UserServiceImpl userService,
// // PasswordEncoder passwordEncoder) {
// // return args -> {
// // if (userService.findByUsername("admin").isEmpty()) {
// // UserEntity admin = new UserEntity();
// // admin.setUsername("admin");
// // admin.setPassword(passwordEncoder.encode("admin123")); // 🔒 Захищений
// пароль
// // admin.setRoles(Set.of("ADMIN", "USER"));
// // userService.saveUser(admin);
// // System.out.println("✅ Created default admin user (admin/admin123)");
// // } else {
// // System.out.println("ℹ️ Admin user already exists.");
// // }
// // };
// // }

// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// http
// .csrf().disable()
// .authorizeHttpRequests((auth) -> auth
// .requestMatchers("/login").permitAll()
// .requestMatchers("/register").permitAll()
// .requestMatchers("/home").permitAll()
// .requestMatchers("/userpage").authenticated()
// .requestMatchers("/studentslist").hasRole("USER")
// .requestMatchers("/form").hasRole("ADMIN")
// .anyRequest().authenticated())
// .formLogin(login -> login
// .loginPage("/login")
// .loginProcessingUrl("/perform_login")
// .defaultSuccessUrl("/home", true)
// .permitAll())
// .logout(logout -> logout
// .logoutUrl("/logout")
// .logoutSuccessUrl("/home")
// .permitAll());

// return http.build();
// }
// }