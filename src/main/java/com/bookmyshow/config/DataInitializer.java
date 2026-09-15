package com.bookmyshow.config;

import com.bookmyshow.dao.RoleDAO;
import com.bookmyshow.dao.UserDAO;
import com.bookmyshow.entity.Role;
import com.bookmyshow.entity.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(
            RoleDAO roleDAO,
            UserDAO userDAO,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Create USER role


            Role userRole = roleDAO.findByName("USER")
                    .orElseGet(() -> {

                        Role role = new Role();
                        role.setName("USER");

                        return roleDAO.save(role);
                    });



            // Create ADMIN role


            Role adminRole = roleDAO.findByName("ADMIN")
                    .orElseGet(() -> {

                        Role role = new Role();
                        role.setName("ADMIN");

                        return roleDAO.save(role);
                    });


            // Create ADMIN user


            String adminEmail = "admin@bookmyshow.com";

            if (!userDAO.existsByEmail(adminEmail)) {

                User admin = new User();

                admin.setName("System Admin");
                admin.setEmail(adminEmail);

                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );

                admin.setPhone("9999999999");
                admin.setRole(adminRole);

                userDAO.save(admin);

                System.out.println(
                        "========================================"
                );

                System.out.println(
                        "ADMIN USER CREATED"
                );

                System.out.println(
                        "Email: " + adminEmail
                );

                System.out.println(
                        "Password: admin123"
                );

                System.out.println(
                        "========================================"
                );
            }
        };
    }
}