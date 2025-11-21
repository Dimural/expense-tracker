package com.expensetracker.expensetracker.category;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import com.expensetracker.expensetracker.user.User;

@Entity
public class Category {

    @Id
    UUID id = UUID.randomUUID();

    String name;
    CategoryTypes type;

    @ManyToOne
    User user;  //user entity is not made yet, that is why there is an error my fellow bakaaaaaaa
    
    String createdAt = "now";
}
