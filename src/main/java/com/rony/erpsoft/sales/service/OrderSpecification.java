package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.sales.dto.OrderSearchDTO;
import com.rony.erpsoft.sales.model.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> build(OrderSearchDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getTransactionId() != null && !dto.getTransactionId().isEmpty()) {
                predicates.add(cb.equal(root.get("transactionId"), dto.getTransactionId()));
            }

            if (dto.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), dto.getStatus()));
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

    public static Specification<Order> transactionIdMatches(String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.isBlank()) {
                predicates.add(cb.like(root.get("transactionId"), "%" + query + "%"));
            }

            // Only select transactionId field
            cq.select(root.get("transactionId")).distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
