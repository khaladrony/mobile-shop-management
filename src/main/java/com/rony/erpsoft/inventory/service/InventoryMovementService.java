package com.rony.erpsoft.inventory.service;

import com.rony.erpsoft.inventory.model.InventoryMovement;

import java.util.List;

public interface InventoryMovementService {

    List<InventoryMovement> findAll();

    InventoryMovement findById(long id);

    InventoryMovement save(InventoryMovement inventoryMovement);
}
