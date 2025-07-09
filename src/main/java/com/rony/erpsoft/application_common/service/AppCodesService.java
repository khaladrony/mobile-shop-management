package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.dto.AppCodesDTO;
import com.rony.erpsoft.application_common.mapper.AppCodesMapper;
import com.rony.erpsoft.application_common.model.AppCodes;
import com.rony.erpsoft.application_common.repo.AppCodesRepository;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.exception.ResourceNotFoundException;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.ModelValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppCodesService {

    private final AppCodesRepository appCodesRepository;
    private final AppCodesMapper appCodesMapper;
    private final ModelValidator modelValidator;
    private final SessionService sessionService;


    public List<AppCodesDTO> findAllByCode(String code) {
        return appCodesRepository.findAllByXcodeAndActiveIsTrue(code)
                .stream()
                .map(appCodesMapper::toDto)
                .toList();
    }

    public Page<AppCodesDTO> findAll(Pageable pageable) {
        return appCodesRepository.findAll(pageable)
                .map(appCodes -> appCodesMapper.toDto(appCodes));
    }

    public List<AppCodesDTO> findAll() {
        return appCodesRepository.findAll()
                .stream()
                .map(appCodes -> appCodesMapper.toDto(appCodes))
                .toList();
    }

    public List<String> findXcodeByXtype(String type) {
        return appCodesRepository.findXcodeByXtypeAndActiveTrue(type);
    }

    public AppCodesDTO findById(long id) {
        return appCodesRepository.findById(id)
                .map(appCodesMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Code not found with id: " + id));
    }

    public AppResponse createAndUpdate(AppCodesDTO requestDTO) {
        try {

            if (requestDTO == null) {
                return AppResponse.build(HttpStatus.BAD_REQUEST)
                        .message("Request cannot be null");
            }

            prepareEntityForSave(requestDTO);

            AppCodes appCodes = appCodesMapper.toEntity(requestDTO);

            if (!modelValidator.isValid(appCodes)) {
                String message = modelValidator.validationMessage(appCodes);
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(message);
            }

            AppCodes saved = appCodesRepository.save(appCodes);

            if (saved.getId() != null && saved.getId() > 0) {
                AppCodesDTO responseDTO = appCodesMapper.toDto(saved);
                return AppResponse.build(HttpStatus.OK).body(responseDTO);
            }
            return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    private void prepareEntityForSave(AppCodesDTO requestDTO) {
        if (requestDTO.getId() == null) {
            requestDTO.setOrganizationId(sessionService.getOrganizationId());
            requestDTO.setActive(true);
            requestDTO.setCreatedBy(sessionService.getUserId());
        }
    }
}
