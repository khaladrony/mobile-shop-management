package com.rony.erpsoft.inventory.itemmaster.service;

import com.rony.erpsoft.application_common.service.GeneralInfoCommonService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.exception.ResourceNotFoundException;
import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterRequestDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterResponseDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.MenuItemDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.MenuItemResponse;
import com.rony.erpsoft.inventory.itemmaster.mapper.ItemMasterMapper;
import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import com.rony.erpsoft.inventory.itemmaster.repository.ItemMasterRepository;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemMasterService {

    private final ItemMasterRepository itemMasterRepository;
    private final ItemMasterMapper itemMasterMapper;
    private final ModelValidator modelValidator;
    private final SessionService sessionService;
    private final GeneralInfoCommonService generalInfoCommonService;

    public List<ItemMaster> findAll() {
        return itemMasterRepository.findAll();
    }

    public ItemMasterResponseDTO findById(long id) {
        return itemMasterRepository.findById(id)
                .map(itemMasterMapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
    }

    public Page<ItemMasterResponseDTO> findAll(Pageable pageable) {
        return itemMasterRepository.findAll(pageable)
                .map(item -> itemMasterMapper.entityToDto(item));
    }

    public AppResponse createAndUpdate(ItemMasterRequestDTO requestDTO) {
        try {

            if (requestDTO == null) {
                return AppResponse.build(HttpStatus.BAD_REQUEST)
                        .message("Request cannot be null");
            }

            prepareEntityForSave(requestDTO);

            ItemMaster itemMaster = itemMasterMapper.requestDTOToEntity(requestDTO);

            if (!modelValidator.isValid(itemMaster)) {
                String message = modelValidator.validationMessage(itemMaster);
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(message);
            }

            ItemMaster saved = itemMasterRepository.save(itemMaster);

            if (saved.getId() != null && saved.getId() > 0) {
                ItemMasterResponseDTO responseDTO = itemMasterMapper.entityToDto(saved);
                return AppResponse.build(HttpStatus.OK).body(responseDTO);
            }

            return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    private void prepareEntityForSave(ItemMasterRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            requestDTO.setItemCode(itemCodeGeneration());
            requestDTO.setOrganizationId(sessionService.getOrganizationId());
            requestDTO.setActive(true);
            requestDTO.setCreatedBy(sessionService.getUserId());
        }
    }

    private String itemCodeGeneration() {
        String prefix = "MOBL";
        int length = 4;
        String lastItemCode = itemMasterRepository.findLastItemCode();

        return generalInfoCommonService.autoCodeGeneration(prefix, length, lastItemCode);
    }

    public List<Map<String, Object>> findActiveItemsForDropDown() {
        return itemMasterRepository.findActiveItemsForDropDown();
    }

    public MenuItemResponse getMenuItem() {
        List<ItemMaster> activeItems = itemMasterRepository.findByActiveTrue();
        List<String> categories = itemMasterRepository.findDistinctCategories();
        List<String> brands = itemMasterRepository.findDistinctBrands()
                .stream()
                .sorted()
                .toList();

        Map<String, List<MenuItemDTO>> itemsByBrand =
                activeItems.stream()
                        .map(itemMasterMapper::entityToMenuDTO)
                        .collect(Collectors.groupingBy(
                                item -> item.getBrand() != null ? item.getBrand() : "Unknown"
                        ));
        return MenuItemResponse.builder()
                .itemsByBrand(itemsByBrand)
                .categories(categories)
                .brands(brands)
                .build();



    }

}
