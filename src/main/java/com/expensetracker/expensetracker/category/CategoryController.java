package com.expensetracker.expensetracker.category;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.expensetracker.category.dto.CategoryResponseDto;
import com.expensetracker.expensetracker.category.dto.CreateCategoryDto;
import com.expensetracker.expensetracker.user.User;
import com.expensetracker.expensetracker.user.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    public CategoryController(CategoryService categoryService, UserRepository userRepository) {
        this.categoryService = categoryService;
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
    

    @PostMapping
    public CategoryResponseDto create(@RequestBody CreateCategoryDto dto) {
        User user = getCurrentUser();
        return categoryService.createCategory(user, dto);

        //IMPORTANT MUST READ ************* this cannot work as planned bc the getCurrentUser is not made yet.
        //However the above code is correct and is supposed to work once the authentication stuff is done
    }

    @GetMapping
    public List<CategoryResponseDto> list() {
        User user = getCurrentUser();
        return categoryService.getCategoriesForUser(user);

        //IMPORTANT MUST READ ************* same thing as in the other method.
        //The code above is correct and is supposed to work once the authentication stuff is done
    }

}
