package com.bookmyshow.service;

import com.bookmyshow.dao.RoleDAO;
import com.bookmyshow.dao.UserDAO;
import com.bookmyshow.dto.AuthResponseDTO;
import com.bookmyshow.dto.LoginRequestDTO;
import com.bookmyshow.dto.RegisterRequestDTO;
import com.bookmyshow.entity.Role;
import com.bookmyshow.entity.User;
import com.bookmyshow.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserDAO userDAO,
            RoleDAO roleDAO,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(RegisterRequestDTO dto) {

        // Check whether email already exists
        if (userDAO.existsByEmail(dto.getEmail())) {
            throw new RuntimeException(
                    "User already exists with email: " + dto.getEmail()
            );
        }

        // Find USER role
        Role userRole = roleDAO.findByName("USER")
                .orElseThrow(() ->
                        new RuntimeException(
                                "USER role not found"
                        )
                );

        // Create user
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // Encrypt password before saving
        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        user.setRole(userRole);

        // Save user
        User savedUser = userDAO.save(user);

        // Create response
        AuthResponseDTO response = new AuthResponseDTO();

        response.setUserId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().getName());

        return response;
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        // Authenticate email + password
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.getEmail(),
                                dto.getPassword()
                        )
                );

        // Get authenticated user
        User user = userDAO.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        String role = user.getRole().getName();

        // Generate JWT
        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        role
                );

        // Create response
        AuthResponseDTO response = new AuthResponseDTO();

        response.setToken(token);
        response.setTokenType("Bearer");
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(role);

        return response;
    }
}