package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryTransactionDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryTransactionSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.ItemLedgerProjection;
import com.rony.erpsoft.inventory.inventorymovement.mapper.InventoryTransactionMapper;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryTransactionMapper inventoryTransactionMapper;

    public List<InventoryTransactionDTO> findAll(InventoryTransactionSearchDTO searchDTO) {
        Specification<InventoryTransaction> spec = InventoryTransactionSpecification.build(searchDTO);

        return inventoryTransactionRepository.findAll(spec)
                .stream()
                .map(item -> inventoryTransactionMapper.entityToDto(item))
                .toList();
    }

    public List<ItemLedgerProjection> findItemLedgerData(InventoryTransactionSearchDTO searchDTO) {
        return inventoryTransactionRepository.findItemLedgerData(
                searchDTO.getFromDate(),
                searchDTO.getToDate(),
                searchDTO.getWarehouse(),
                searchDTO.getItemCode()
        );
    }

    public List<ItemLedgerProjection> findItemLedgerSummaryData(InventoryTransactionSearchDTO searchDTO) {
        return inventoryTransactionRepository.findItemLedgerSummaryData(
                searchDTO.getFromDate(),
                searchDTO.getToDate(),
                searchDTO.getWarehouse(),
                searchDTO.getItemCode()
        );
    }
}
