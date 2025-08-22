package com.rony.erpsoft.application_common.service.specification;

import com.rony.erpsoft.application_common.model.CustomerInfo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CustomerInfoSpecification {

    public static Specification<CustomerInfo> matchesNameOrMobile(String query) {
        return (root, cq, cb) -> {
            if (query == null || query.isBlank()) {
                return cb.conjunction(); // no filter
            }

            String likeQuery = "%" + query + "%";

            Predicate mobilePredicate = cb.like(root.get("mobileNumber"), likeQuery);
            Predicate namePredicate = cb.like(root.get("customerName"), likeQuery);

            return cb.or(mobilePredicate, namePredicate);
        };
    }
}
