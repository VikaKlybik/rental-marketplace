package com.bsuir.repository;

import com.bsuir.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.amount) FROM Transaction t")
    BigDecimal findAllAmount();
    List<Transaction> findAllByCreatedAtBetween(LocalDateTime createdAt, LocalDateTime createdAt2);
}