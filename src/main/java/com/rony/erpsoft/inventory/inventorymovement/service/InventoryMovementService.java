package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;

import java.util.List;

public interface InventoryMovementService {

    List<InventoryMovement> findAll();

    InventoryMovement findById(long id);

    InventoryMovement save(InventoryMovement inventoryMovement);
}
