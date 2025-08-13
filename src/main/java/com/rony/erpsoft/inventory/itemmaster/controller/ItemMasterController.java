package com.rony.erpsoft.inventory.itemmaster.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterRequestDTO;
import com.rony.erpsoft.inventory.itemmaster.dto.ItemMasterResponseDTO;
import com.rony.erpsoft.inventory.itemmaster.service.ItemMasterService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.rony.erpsoft.utils.ApplicationConstants.FILTER;
import static com.rony.erpsoft.utils.ApplicationConstants.INVENTORY_MASTER_BASE_URL;
import static com.rony.erpsoft.utils.ApplicationConstants.SORT_BY_ID;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW_PAGE;


@RestController
@RequestMapping(INVENTORY_MASTER_BASE_URL)
@AllArgsConstructor
public class ItemMasterController extends AppProperty {

    private final AppUtil appUtil;
    private final ItemMasterService itemMasterService;

    @RequestMapping(value = VIEW, method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @PostMapping(FILTER)
    public AppResponse<Object> filter(
            @PageableDefault(size = 10, sort = SORT_BY_ID, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestBody Map<String, Object> params
    ) {
        try {
            return AppResponse
                    .build(HttpStatus.OK)
                    .body(itemMasterService.findAll(pageable));
        } catch (Exception ex) {
            return AppResponse
                    .build(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }
    }

    @PostMapping(value = "/save")
    public AppResponse<ItemMasterResponseDTO> save(@RequestBody ItemMasterRequestDTO requestDTO) {
        return itemMasterService.createAndUpdate(requestDTO);
    }

    @PutMapping(value = "/update")
    public AppResponse<ItemMasterResponseDTO> update(@RequestBody ItemMasterRequestDTO requestDTO) {
        return itemMasterService.createAndUpdate(requestDTO);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> get(@PathVariable("id") long id) {
        return AppResponse.build(HttpStatus.OK).body(itemMasterService.findById(id));
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public AppResponse<Object> getActiveItems() {
        try {
            List<Map<String, Object>> list = itemMasterService.findActiveItemsForDropDown();
            if (!list.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(list);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Items not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PostMapping("/upload-image")
    public AppResponse<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("moduleName") String moduleName
    ) {
        try {
            if (file.isEmpty()) {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("No file selected");
            }

            File dir = new File(appUtil.getUploadDir(moduleName));
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(appUtil.getUploadDir(moduleName), fileName);
            Files.write(filePath, file.getBytes());

            // Here you would save fileName in DB instead of full path
            return AppResponse.build(HttpStatus.OK).body(fileName);
        } catch (IOException e) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
    }

    @GetMapping("/images/{moduleName}/{filename}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String moduleName,
            @PathVariable String filename
    ) {
        try {
            Path filePath = Paths.get(appUtil.getUploadDir(moduleName)).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or detect dynamically
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
