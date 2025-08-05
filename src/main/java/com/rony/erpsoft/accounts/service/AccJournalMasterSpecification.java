package com.rony.erpsoft.accounts.service;

import com.rony.erpsoft.accounts.model.AccJournalMaster;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AccJournalMasterSpecification {

    public static Specification<AccJournalMaster> voucherNoMatches(
            String voucherType, String status, String query, Long createdBy
    ) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.isBlank()) {
                predicates.add(cb.like(root.get("voucherNo"), "%" + query + "%"));
            }

            if (voucherType != null) {
                predicates.add(cb.equal(root.get("voucherType"), voucherType));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            predicates.add(cb.equal(root.get("createdBy"), createdBy));

            cq.select(root.get("voucherNo")).distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
