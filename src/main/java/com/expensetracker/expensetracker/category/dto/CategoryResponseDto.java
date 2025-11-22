package com.expensetracker.expensetracker.category.dto;

import java.time.Instant;
import java.util.UUID;

import com.expensetracker.expensetracker.category.CategoryTypes;

public record CategoryResponseDto(
    UUID id,
    String name,
    CategoryTypes type,
    Instant createdAt
) {
    
}
