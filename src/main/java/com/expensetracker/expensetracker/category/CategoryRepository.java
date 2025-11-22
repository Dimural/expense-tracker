package com.expensetracker.expensetracker.category;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.expensetracker.user.User;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByUser(User user);

    Optional<Category> findByUserAndName(User user, String name);
    
} 
