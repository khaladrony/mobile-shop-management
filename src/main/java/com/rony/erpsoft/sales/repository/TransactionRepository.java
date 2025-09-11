package com.rony.erpsoft.sales.repository;

import com.rony.erpsoft.sales.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    Page<Transaction> findAllByOrderByIdDesc(Pageable pageable);
}
