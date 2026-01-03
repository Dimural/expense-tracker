package com.expensetracker.expensetracker.analytics.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.expensetracker.expensetracker.category.CategoryTypes;

public record MonthlyCategoryBreakdown(
    UUID categoryId,
    String categoryName,
    CategoryTypes categoryType,
    BigDecimal totalAmount
) {

}
