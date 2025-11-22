package com.expensetracker.expensetracker.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.expensetracker.expensetracker.category.dto.CategoryResponseDto;
import com.expensetracker.expensetracker.category.dto.CreateCategoryDto;
import com.expensetracker.expensetracker.user.User;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto createCategory(User user, CreateCategoryDto dto) {
        categoryRepository.findByUserAndName(user, dto.name())
            .ifPresent(existingCategory -> {
                throw new IllegalArgumentException("Category with the same name already exists for this user.");
            });
        Category category = new Category(dto.name(), dto.type(), user);
        Category savedCategory = categoryRepository.save(category);
        return toResponseDto(savedCategory);
    }

    public CategoryResponseDto toResponseDto(Category category) {
        return new CategoryResponseDto(
            category.getId(),
            category.getName(),
            category.getType(),
            category.getCreatedAt()
        );
    }

    public List<CategoryResponseDto> getCategoriesForUser(User user) {
        List<Category> categories = categoryRepository.findByUser(user);
        return categories.stream()
                .map(this::toResponseDto)
                .toList();
    }
}
