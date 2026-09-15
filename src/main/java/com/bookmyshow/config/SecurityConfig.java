package com.bookmyshow.config;

import com.bookmyshow.security.CustomUserDetailsService;
import com.bookmyshow.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Authentication APIs
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        // Public movie browsing
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/movies/**"
                        ).permitAll()

                        // Public theatre browsing
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/theatres/**"
                        ).permitAll()

                        // Public screen browsing
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/screens/**"
                        ).permitAll()

                        // Public screen-seat browsing
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/screen-seats/**"
                        ).permitAll()

                        // Public show browsing
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/shows/**"
                        ).permitAll()

                        // USER APIs
                        .requestMatchers(
                                "/api/v1/shows/*/hold-seats"
                        ).hasRole("USER")

                        .requestMatchers(
                                "/api/v1/bookings/**"
                        ).hasRole("USER")

                        // Ticket APIs
                        .requestMatchers(
                                "/api/v1/tickets/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // ADMIN movie APIs
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/api/movies/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/api/movies/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/api/movies/**"
                        ).hasRole("ADMIN")

                        // ADMIN theatre APIs
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/api/theatres/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/api/theatres/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/api/theatres/**"
                        ).hasRole("ADMIN")

                        // ADMIN screen APIs
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/api/screens/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/api/screens/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/api/screens/**"
                        ).hasRole("ADMIN")

                        // ADMIN screen-seat APIs
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/api/screen-seats/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/api/screen-seats/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/api/screen-seats/**"
                        ).hasRole("ADMIN")

                        // ADMIN show APIs
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/api/shows/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/api/shows/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/api/shows/**"
                        ).hasRole("ADMIN")

                        // Everything else requires login
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}