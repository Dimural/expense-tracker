package com.expensetracker.expensetracker.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTransactionDto(@NotNull @DecimalMin(value = "0.01") BigDecimal amount, 
    @NotNull LocalDate date,
    @Size(max = 500) String description,
    @NotNull UUID categoryId) {

}
