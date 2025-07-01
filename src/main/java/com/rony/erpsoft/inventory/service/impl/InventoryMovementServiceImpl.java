package com.rony.erpsoft.inventory.service.impl;

import com.rony.erpsoft.inventory.model.InventoryMovement;
import com.rony.erpsoft.inventory.repository.InventoryMovementRepository;
import com.rony.erpsoft.inventory.service.InventoryMovementService;
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
