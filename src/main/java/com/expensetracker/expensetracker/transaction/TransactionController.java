package com.expensetracker.expensetracker.transaction;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.expensetracker.transaction.dto.CreateTransactionDto;
import com.expensetracker.expensetracker.transaction.dto.TransactionResponseDto;
import com.expensetracker.expensetracker.transaction.dto.UpdateTransactionDto;
import com.expensetracker.expensetracker.user.User;
import com.expensetracker.expensetracker.user.UserRepository;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final UserRepository userRepository;

    public TransactionController(TransactionService transactionService, UserRepository userRepository){
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }
    private User getCurrentUser(){
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
    
    @PostMapping
    public TransactionResponseDto createTransation(@Valid @RequestBody CreateTransactionDto dto){
        User user = getCurrentUser();
        return transactionService.create(user,dto);
        
    }

    @GetMapping
    public List<TransactionResponseDto> list(){
        User user = getCurrentUser();
        return transactionService.list(user);
    }

    @GetMapping("/{id}")
    public TransactionResponseDto get(@PathVariable UUID id){
        User user = getCurrentUser();
        return transactionService.get(user,id);
    }

    @PutMapping("/{id}")
    public TransactionResponseDto update(@PathVariable UUID id, @Valid @RequestBody UpdateTransactionDto dto){
        User user = getCurrentUser();
        return transactionService.update(user,id,dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id)
    {
        User user = getCurrentUser();
        transactionService.delete(user, id);
    }
    
}
