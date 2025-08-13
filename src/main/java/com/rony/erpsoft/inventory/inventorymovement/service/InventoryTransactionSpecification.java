package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryTransactionSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryTransactionSpecification {

    public static Specification<InventoryTransaction> build(InventoryTransactionSearchDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("transactionDate"),
                        dto.getFromDate().with(LocalTime.MIN)
                ));
            }

            if (dto.getToDate() != null) {
                // include full day by setting time to 23:59:59.999
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("transactionDate"),
                        dto.getToDate().with(LocalTime.MAX) // 23:59:59.999999999
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
