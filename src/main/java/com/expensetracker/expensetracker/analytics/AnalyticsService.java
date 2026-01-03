package com.expensetracker.expensetracker.analytics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.expensetracker.expensetracker.analytics.dto.MonthlyAnalyticsResponse;
import com.expensetracker.expensetracker.analytics.dto.MonthlyCategoryBreakdown;
import com.expensetracker.expensetracker.category.CategoryTypes;
import com.expensetracker.expensetracker.transaction.TransactionRepository;
import com.expensetracker.expensetracker.user.User;

@Service
public class AnalyticsService {
    private final TransactionRepository transactionRepository;

    public AnalyticsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public MonthlyAnalyticsResponse monthly(User user, int year, int month){
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        List<MonthlyCategoryBreakdown> breakdown = transactionRepository.sumByCategoryForMonth(user, start, end);
        BigDecimal income = breakdown.stream()
            .filter(b-> b.categoryType() == CategoryTypes.INCOME)
            .map(MonthlyCategoryBreakdown::totalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = breakdown.stream()
            .filter(b-> b.categoryType() == CategoryTypes.EXPENSE)
            .map(MonthlyCategoryBreakdown::totalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal net = income.subtract(expense);

        return new MonthlyAnalyticsResponse(year, month, income, expense, net, breakdown);
    }
    

    
}