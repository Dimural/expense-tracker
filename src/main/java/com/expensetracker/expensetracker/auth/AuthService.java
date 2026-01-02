package com.expensetracker.expensetracker.auth;

import org.springframework.stereotype.Service;
import com.expensetracker.expensetracker.user.UserService;
import com.expensetracker.expensetracker.user.User;
import java.util.Optional;

@Service
public class AuthService { 
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        
        // 1) create the user (this will normalize email, hash password, etc.)
        userService.createUser(request.getEmail(), request.getPassword());

        // 2) return a simple response DTO for the API
        return new AuthResponse("User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        Optional<User> userOpt = userService.getUserByEmail(normalizedEmail);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userOpt.get();

        boolean ok = userService.checkPassword(user, request.getPassword());
        if (!ok) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse("Login successful", token);

    }
}
