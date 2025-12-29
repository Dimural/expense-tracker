package com.expensetracker.expensetracker.transaction;


import com.expensetracker.expensetracker.category.Category;
import com.expensetracker.expensetracker.user.User;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expensetracker.expensetracker.category.CategoryRepository;
import com.expensetracker.expensetracker.transaction.dto.CreateTransactionDto;
import com.expensetracker.expensetracker.transaction.dto.TransactionResponseDto;
import com.expensetracker.expensetracker.transaction.dto.UpdateTransactionDto;


@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    
    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public TransactionResponseDto create(User user, CreateTransactionDto dto) {
        Category category = categoryRepository.findById(dto.categoryId()).filter(cat-> cat.getUser().equals(user))
            .orElseThrow(() -> new IllegalArgumentException("Category not found for the user"));

        Transaction transaction = new Transaction(
            dto.amount(),
            dto.description(),
            dto.date(),
            category,
            user
        );
        return toDto(transactionRepository.save(transaction));
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDto> list(User user) {
        return transactionRepository.findByUserOrderByDateDesc(user).stream()
            .map(this::toDto)
            .toList();
    }
    @Transactional(readOnly = true)
    public TransactionResponseDto get(User user, UUID id) {
        Transaction tx = transactionRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        return toDto(tx);
    }

    @Transactional
    public TransactionResponseDto update(User user, UUID id, UpdateTransactionDto dto) {
        Transaction tx = transactionRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (dto.amount() != null) tx.setAmount(dto.amount());
        if (dto.date() != null) tx.setDate(dto.date());
        if (dto.description() != null) tx.setDescription(dto.description());

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                .filter(cat -> cat.getUser().equals(user))
                .orElseThrow(() -> new IllegalArgumentException("Category not found for user"));
            tx.setCategory(category);
        }

        return toDto(transactionRepository.save(tx));
    }

    @Transactional
    public void delete(User user, UUID id) {
        Transaction tx = transactionRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transactionRepository.delete(tx);
    }

    private TransactionResponseDto toDto(Transaction tx) {
        return new TransactionResponseDto(
            tx.getId(),
            tx.getAmount(),
            tx.getDate(),
            tx.getDescription(),
            tx.getCategory().getType(),
            tx.getCategory().getId(),
            tx.getCategory().getName(),
            tx.getCreatedAt()
        );
    }


}
