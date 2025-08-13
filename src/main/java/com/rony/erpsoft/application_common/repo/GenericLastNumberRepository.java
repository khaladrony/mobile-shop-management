package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.TransactionNumberConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenericLastNumberRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public String findLastNumber(TransactionNumberConfig config, String transactionType) {
        String jpql = buildQuery(config);
        var query = entityManager.createQuery(jpql, String.class)
                .setMaxResults(1);

        if (config.getTypeField() != null && !config.getTypeField().isEmpty()) {
            query.setParameter("transactionType", transactionType);
        }

        List<String> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    private String buildQuery(TransactionNumberConfig config) {
        String alias = "t";
        StringBuilder query = new StringBuilder();

        query.append("SELECT ").append(alias).append(".").append(config.getNumberField())
                .append(" FROM ").append(config.getEntityName()).append(" ").append(alias)
                .append(" WHERE 1=1"); // always true, so we can append conditions easily

        // Add type filter if configured
        if (config.getTypeField() != null && !config.getTypeField().isEmpty()) {
            query.append(" AND ").append(alias).append(".").append(config.getTypeField())
                    .append(" = :transactionType");
        }
        query.append(" ORDER BY ").append(alias).append(".").append("id").append(" DESC");

        return query.toString();
    }
}
