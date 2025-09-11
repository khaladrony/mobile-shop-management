package com.rony.erpsoft.inventory.inventorymovement.repository;

import com.rony.erpsoft.inventory.inventorymovement.model.InventoryDefaultSetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryDefaultSetupRepository extends JpaRepository<InventoryDefaultSetup, Long> {

    Optional<InventoryDefaultSetup> findByBranchCode(String branchCode);
}
