package com.expensetracker.expensetracker.auth;

import org.springframework.stereotype.Service;
import com.expensetracker.expensetracker.user.UserService;
import com.expensetracker.expensetracker.user.User;
import java.util.Optional;

@Service
public class AuthService { 
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
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

        return new AuthResponse("Login successful");
    }
}
