package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.application_common.service.GeneralInfoCommonService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.exception.ResourceNotFoundException;
import com.rony.erpsoft.inventory.enums.InventoryAction;
import com.rony.erpsoft.inventory.enums.InventoryStatus;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementRequestDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementResponseDTO;
import com.rony.erpsoft.inventory.inventorymovement.mapper.InventoryMovementMapper;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryMovement;
import com.rony.erpsoft.inventory.inventorymovement.repository.InventoryMovementRepository;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.rony.erpsoft.utils.ApplicationConstants.RECEIPT_SIGN;

@Service
@AllArgsConstructor
public class InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementMapper inventoryMovementMapper;
    private final ModelValidator modelValidator;
    private final SessionService sessionService;
    private final GeneralInfoCommonService generalInfoCommonService;

    public Page<InventoryMovementResponseDTO> findAll(Pageable pageable) {
        return inventoryMovementRepository.findAll(pageable)
                .map(item -> inventoryMovementMapper.entityToDto(item));
    }

    public InventoryMovementResponseDTO findById(long id) {
        return inventoryMovementRepository.findById(id)
                .map(inventoryMovementMapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

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
            requestDTO.setTransactionId(transactionIdGeneration());
            requestDTO.setOrganizationId(sessionService.getOrganizationId());
            requestDTO.setSign(RECEIPT_SIGN);
            requestDTO.setAction(InventoryAction.RECEIPT);
            requestDTO.setStatus(InventoryStatus.OPEN);
            requestDTO.setYear(requestDTO.getTransactionDate().getYear());
            requestDTO.setMonth(requestDTO.getTransactionDate().getMonthValue());
            requestDTO.setCreatedBy(sessionService.getUserId());
        } else {
            requestDTO.setUpdatedBy(sessionService.getUserId());
        }
    }

    private String transactionIdGeneration() {
        String prefix = "RE--";
        int length = 6;
        String lastTransactionId = inventoryMovementRepository.findLastTransactionId(RECEIPT_SIGN);

        return generalInfoCommonService.autoCodeGeneration(prefix, length, lastTransactionId);
    }
}
