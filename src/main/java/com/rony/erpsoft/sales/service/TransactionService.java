package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.sales.dto.TransactionDTO;
import com.rony.erpsoft.sales.mapper.TransactionMapper;
import com.rony.erpsoft.sales.model.Transaction;
import com.rony.erpsoft.sales.model.enums.POSTransactionType;
import com.rony.erpsoft.sales.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public AppResponse createAndUpdate(TransactionDTO dto) {
        try {
            TransactionDTO transaction = mapper.entityToDto(repository.save(mapper.dtoToEntity(dto)));

            return AppResponse.build(HttpStatus.OK).body(transaction);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    public Page<TransactionDTO> findAll(Pageable pageable) {
        return repository.findAllByOrderByIdDesc(pageable)
                .map(mapper::entityToDto);
    }

    public TransactionDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
    }

    public Map<POSTransactionType, BigDecimal> transactionSummary(String startDate, String endDate) {
        LocalDateTime fromDate = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime toDate = LocalDate.parse(endDate).atTime(23, 59, 59);

        List<Transaction> transactions = repository.findByTransactionDateBetween(fromDate, toDate);

        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getType,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));
    }
}
