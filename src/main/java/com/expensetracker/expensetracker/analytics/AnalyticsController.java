package com.expensetracker.expensetracker.analytics;

import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.expensetracker.analytics.dto.MonthlyAnalyticsResponse;
import com.expensetracker.expensetracker.user.User;
import com.expensetracker.expensetracker.user.UserRepository;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    private final UserRepository userRepository;

    public AnalyticsController(AnalyticsService analyticsService, UserRepository userRepository) {
        this.analyticsService = analyticsService;
        this.userRepository = userRepository;
    }
    private User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
    
    @GetMapping("/monthly")
    public MonthlyAnalyticsResponse monthly(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        LocalDate now = LocalDate.now();
        int y = (year!= null)?year:now.getYear();
        int m = (month!=null)?month:now.getMonthValue();


        User user = getCurrentUser();
        return analyticsService.monthly(user, y, m);
    }

    
}