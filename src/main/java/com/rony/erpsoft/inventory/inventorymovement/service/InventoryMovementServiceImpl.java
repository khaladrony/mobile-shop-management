package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryMovementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryMovementServiceImpl implements InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;

    @Override
    public List<InventoryMovement> findAll() {
        return List.of();
    }

    @Override
    public InventoryMovement findById(long id) {
        return null;
    }

    @Override
    public InventoryMovement save(InventoryMovement inventoryMovement) {
        return inventoryMovementRepository.save(inventoryMovement);
    }
}
