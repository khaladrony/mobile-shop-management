package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.application_common.service.GeneralInfoCommonService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.exception.ResourceNotFoundException;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementItemRequestDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementRequestDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementResponseDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.mapper.InventoryMovementMapper;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryMovementRepository;
import com.rony.erpsoft.utils.ModelValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementMapper inventoryMovementMapper;
    private final ModelValidator modelValidator;
    private final GeneralInfoCommonService generalInfoCommonService;

    public Page<InventoryMovementResponseDTO> findAll(InventoryMovementSearchDTO searchDTO, Pageable pageable) {
        Specification<InventoryMovement> spec = InventoryMovementSpecification.build(searchDTO);

        return inventoryMovementRepository.findAll(spec, pageable)
                .map(item -> inventoryMovementMapper.entityToDto(item));
    }

    public InventoryMovementResponseDTO findById(long id) {
        return inventoryMovementRepository.findById(id)
                .map(inventoryMovementMapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
    }

    public List<String> searchTransactionIds(InventoryAction action, String query) {
        Specification<InventoryMovement> spec = InventoryMovementSpecification.transactionIdMatches(query, action);
        return inventoryMovementRepository.findAll(spec)
                .stream()
                .map(InventoryMovement::getTransactionId)
                .distinct()
                .collect(Collectors.toList());
    }


    public InventoryMovement save(InventoryMovement inventoryMovement) {
        return inventoryMovementRepository.save(inventoryMovement);
    }

    public AppResponse createAndUpdate(InventoryMovementRequestDTO movementRequestDTO) {

        try {

            clientDataValidate(movementRequestDTO);

            prepareEntityForSave(movementRequestDTO);

            InventoryMovement inventoryMovement = inventoryMovementMapper.requestDTOToEntity(movementRequestDTO);

            if (!modelValidator.isValid(inventoryMovement)) {
                String message = modelValidator.validationMessage(inventoryMovement);
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(message);
            }

            InventoryMovement saved = inventoryMovementRepository.save(inventoryMovement);

            if (saved.getId() != null && saved.getId() > 0) {
                InventoryMovementResponseDTO responseDTO = inventoryMovementMapper.entityToDto(saved);
                return AppResponse.build(HttpStatus.OK).body(responseDTO);
            }

            return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    public AppResponse clientDataValidate(InventoryMovementRequestDTO movementRequestDTO) {

        try {

            if (movementRequestDTO.getDetails().isEmpty()) {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Inventory movement item not found!");
            }

            return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(movementRequestDTO));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    private void prepareEntityForSave(InventoryMovementRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            requestDTO.setTransactionId(transactionIdGeneration(requestDTO.getAction()));
            requestDTO.setTransactionDate(requestDTO.getTransactionDate().with(LocalTime.now()));

            List<InventoryMovementItemRequestDTO> details = requestDTO.getDetails();
            if (details != null) {
                for (int i = 0; i < details.size(); i++) {
                    InventoryMovementItemRequestDTO item = details.get(i);
                    item.setLineNumber(i + 1);
                }
            }
        }
    }

    private String transactionIdGeneration(InventoryAction action) {
        String prefix = setPrefix(action);
        int sign = action.getSign();
        int length = 6;
        String lastTransactionId = inventoryMovementRepository.findLastTransactionId(sign);

        return generalInfoCommonService.autoCodeGeneration(prefix, length, lastTransactionId);
    }

    private String setPrefix(InventoryAction action) {
        return switch (action) {
            case RECEIPT -> "RE--";
            case ISSUE -> "IS--";
            case TRANSFER -> "TO--";
        };
    }
}
