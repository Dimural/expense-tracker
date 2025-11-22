package com.expensetracker.expensetracker.category;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.expensetracker.expensetracker.user.User;
import jakarta.persistence.Column;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryTypes type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  
    
    @Column(nullable = false)
    private Instant createdAt = Instant.now();


    public Category() {
        
    }

    public Category(String name, CategoryTypes type, User user) {
        this.name = name;
        this.type = type;
        this.user = user;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(CategoryTypes type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getName() {
        return name;
    }

    public CategoryTypes getType() {
        return type;
    }
    public User getUser() {
        return user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}