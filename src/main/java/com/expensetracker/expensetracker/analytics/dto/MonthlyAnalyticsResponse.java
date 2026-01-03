package com.expensetracker.expensetracker.analytics.dto;

import java.math.BigDecimal;
import java.util.List;

public record MonthlyAnalyticsResponse(
    int year,
    int month,
    BigDecimal totalIncome,
    BigDecimal totalExpense,
    BigDecimal net,
    List<MonthlyCategoryBreakdown> breakdown
) {

}
