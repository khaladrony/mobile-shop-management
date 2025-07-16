package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryMovementSpecification {

    public static Specification<InventoryMovement> build(InventoryMovementSearchDTO dto) {
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

            if (dto.getTransactionId() != null && !dto.getTransactionId().isEmpty()) {
                predicates.add(cb.equal(root.get("transactionId"), dto.getTransactionId()));
            }

            if (dto.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), dto.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
