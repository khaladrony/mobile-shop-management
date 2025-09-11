package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryDefaultSetupDTO;
import com.rony.erpsoft.inventory.inventorymovement.mapper.InventoryDefaultSetupMapper;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryDefaultSetupRepository;
import com.rony.erpsoft.user_auth.service.SessionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class InventoryDefaultSetupService {

    private final InventoryDefaultSetupRepository repository;
    private final InventoryDefaultSetupMapper mapper;
    private final SessionService sessionService;

    public InventoryDefaultSetupDTO findByBranchCode(){
        return repository.findByBranchCode(sessionService.getBranch().getCode())
                .map(mapper::entityToDto)
                .orElse(null);
    }
}
