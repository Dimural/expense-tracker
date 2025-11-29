package com.expensetracker.expensetracker.category.dto;
import com.expensetracker.expensetracker.category.CategoryTypes;

public record CreateCategoryDto(String name, CategoryTypes type) {
    
}
