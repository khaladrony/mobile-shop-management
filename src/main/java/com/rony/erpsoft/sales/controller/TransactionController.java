package com.rony.erpsoft.sales.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.sales.dto.OrderRequestDTO;
import com.rony.erpsoft.sales.dto.OrderSearchDTO;
import com.rony.erpsoft.sales.dto.TransactionDTO;
import com.rony.erpsoft.sales.service.TransactionService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static com.rony.erpsoft.utils.ApplicationConstants.FILTER;
import static com.rony.erpsoft.utils.ApplicationConstants.POS_TRANSACTION_BASE_URL;
import static com.rony.erpsoft.utils.ApplicationConstants.SALES_VIEW_PAGE;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW;

@RestController
@RequestMapping(POS_TRANSACTION_BASE_URL)
@AllArgsConstructor
public class TransactionController extends AppProperty {

    private final AppUtil appUtil;
    private final TransactionService service;

    @GetMapping(value = VIEW)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView(SALES_VIEW_PAGE);
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @PostMapping(value = FILTER)
    public AppResponse<Object> filter(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestBody OrderSearchDTO searchDTO
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

    @PostMapping(value = "/save")
    public AppResponse save(@RequestBody TransactionDTO requestDTO){
        return service.createAndUpdate(requestDTO);
    }

    @PutMapping(value = "/update")
    public AppResponse update(@RequestBody TransactionDTO requestDTO) {
        return service.createAndUpdate(requestDTO);
    }

    @GetMapping(value = "/transactions/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getTransaction(@PathVariable("id") long id) {
        return AppResponse.build(HttpStatus.OK).body(service.getById(id));
    }
}
