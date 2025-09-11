package com.rony.erpsoft.user_auth.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.sales.dto.OrderSearchDTO;
import com.rony.erpsoft.user_auth.dto.BranchDTO;
import com.rony.erpsoft.user_auth.service.BranchService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.rony.erpsoft.utils.ApplicationConstants.FILTER;

@RestController
@RequestMapping("user_auth/branches")
@AllArgsConstructor
public class BranchController extends AppProperty {

    private final BranchService service;
    private final AppUtil appUtil;;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("user_auth/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
        return modelAndView;
    }

    @GetMapping(value = "/all")
    public AppResponse<List<BranchDTO>> getAll() {
        List<BranchDTO> models = service.findAll();
        if (!models.isEmpty()) {
            return AppResponse.build(HttpStatus.OK).body(models);
        } else {
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No branch found");
        }
    }

    @PostMapping(value = "/save")
    public AppResponse save(@RequestBody BranchDTO requestDTO){
        return service.createAndUpdate(requestDTO);
    }

    @PutMapping(value = "/update")
    public AppResponse update(@RequestBody BranchDTO requestDTO) {
        return service.createAndUpdate(requestDTO);
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getTransaction(@PathVariable("id") long id) {
        return AppResponse.build(HttpStatus.OK).body(service.getById(id));
    }

    @PostMapping(value = FILTER)
    public AppResponse<Object> filter(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        try {
            return AppResponse
                    .build(HttpStatus.OK)
                    .body(service.findAll(pageable));
        } catch (Exception ex) {
            return AppResponse
                    .build(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }
    }

}
