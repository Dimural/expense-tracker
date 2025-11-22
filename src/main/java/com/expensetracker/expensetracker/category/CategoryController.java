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

        return userRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No user found in database"));

       //this cannot be made yet, it needs the authentication stuff and blah blah blah. it is supposesd to get the current logged in user
       // I just made it get first user, THIS CODE IS INCORRECT BUT TEMPORARY DO NOT FORGET HAHAHAHAAAHAHAHAHAHAHA
    }

    @PostMapping
    public CategoryResponseDto create(@RequestBody CreateCategoryDto dto) {
        User user = getCurrentUser();
        return categoryService.createCategory(user, dto);

        //IMPORTANT MUST READ ************* this cannot work as planned bc the getCurrentUser is not made yet.
        //However the above code is correct and is supposed to work once the authentication stuff is done
    }

    public List<CategoryResponseDto> list() {
        User user = getCurrentUser();
        return categoryService.getCategoriesForUser(user);

        //IMPORTANT MUST READ ************* same thing as in the other method.
        //The code above is correct and is supposed to work once the authentication stuff is done
    }

}
