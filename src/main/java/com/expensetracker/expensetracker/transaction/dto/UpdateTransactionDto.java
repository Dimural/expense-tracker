package com.expensetracker.expensetracker.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Size;

public record UpdateTransactionDto(
    BigDecimal amount,
    LocalDate date,
    @Size(max = 500) String description,
    UUID categoryId
) {}
