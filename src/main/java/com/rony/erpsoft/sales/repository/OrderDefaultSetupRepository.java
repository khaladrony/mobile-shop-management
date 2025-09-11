package com.rony.erpsoft.sales.repository;

import com.rony.erpsoft.sales.model.OrderDefaultSetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDefaultSetupRepository extends JpaRepository<OrderDefaultSetup, Long> {

    Optional<OrderDefaultSetup> findByBranchCode(String branchCode);
}
