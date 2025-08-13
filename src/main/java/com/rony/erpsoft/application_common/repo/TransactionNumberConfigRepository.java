package com.rony.erpsoft.application_common.repo;

import com.rony.erpsoft.application_common.model.TransactionNumberConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionNumberConfigRepository extends JpaRepository<TransactionNumberConfig, Long> {

    Optional<TransactionNumberConfig> findByOrganizationIdAndModuleAndTransactionType(Long organizationId, String module, String transactionType);
}
