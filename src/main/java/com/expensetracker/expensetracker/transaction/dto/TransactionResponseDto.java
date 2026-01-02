package com.expensetracker.expensetracker.transaction.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.expensetracker.expensetracker.category.CategoryTypes;

public record TransactionResponseDto(
    UUID id,
    BigDecimal amount,
    LocalDate date,
    String description,
    CategoryTypes categoryType,
    UUID categoryId,
    String categoryname,
    Instant createdAt
) {

}
