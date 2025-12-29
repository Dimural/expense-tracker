package com.expensetracker.expensetracker.transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.expensetracker.user.User;


public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUserOrderByDateDesc(User user);
    Optional<Transaction> findByIdAndUser(UUID id, User user);

    
}
