package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import jakarta.persistence.criteria.Path;
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

            if (dto.getAction() != null) {
                predicates.add(cb.equal(root.get("action"), dto.getAction()));
            }

            if (dto.getSortField() != null && !dto.getSortField().isEmpty()) {
                Path<Object> sortPath = root.get(dto.getSortField());
                if ("desc".equalsIgnoreCase(dto.getSortDirection())) {
                    query.orderBy(cb.desc(sortPath));
                } else {
                    query.orderBy(cb.asc(sortPath));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<InventoryMovement> transactionIdMatches(String query, InventoryAction action) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.isBlank()) {
                predicates.add(cb.like(root.get("transactionId"), "%" + query + "%"));
            }

            if (action != null) {
                predicates.add(cb.equal(root.get("action"), action));
            }

            // Only select transactionId field
            cq.select(root.get("transactionId")).distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
