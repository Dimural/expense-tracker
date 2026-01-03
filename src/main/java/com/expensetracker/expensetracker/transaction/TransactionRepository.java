package com.expensetracker.expensetracker.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.expensetracker.expensetracker.analytics.dto.MonthlyCategoryBreakdown;
import com.expensetracker.expensetracker.user.User;


public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUserOrderByDateDesc(User user);
    Optional<Transaction> findByIdAndUser(UUID id, User user);
    
    @Query("""
    select new com.expensetracker.expensetracker.analytics.dto.MonthlyCategoryBreakdown(
        t.category.id,
        t.category.name,
        t.category.type,
        sum(t.amount)
    )
    from Transaction t
    where t.user = :user
    and t.date between :start and :end
    group by t.category.id, t.category.name, t.category.type
    """)
    List<MonthlyCategoryBreakdown> sumByCategoryForMonth(User user, LocalDate start, LocalDate end);


    
}
