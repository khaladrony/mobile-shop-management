package com.rony.erpsoft.application_common.controller;

import com.rony.erpsoft.application_common.dto.AppCodesDTO;
import com.rony.erpsoft.application_common.service.AppCodesService;
import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import java.util.Map;

import static com.rony.erpsoft.utils.ApplicationConstants.FILTER;
import static com.rony.erpsoft.utils.ApplicationConstants.SORT_BY_ID;

@RestController
@AllArgsConstructor
@RequestMapping("/application_common/app_codes")
public class AppCodesController extends AppProperty {

    private final AppUtil appUtil;
    private final AppCodesService appCodesService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("application_common/view");
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
                    .body(appCodesService.findAll(pageable));
        } catch (Exception ex) {
            return AppResponse
                    .build(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }
    }

    @PostMapping(value = "/save")
    public AppResponse<AppCodesDTO> save(@RequestBody AppCodesDTO appCodesDTO){
        return appCodesService.createAndUpdate(appCodesDTO);
    }

    @PutMapping(value = "/update")
    public AppResponse<AppCodesDTO> update(@RequestBody AppCodesDTO appCodesDTO) {
        return appCodesService.createAndUpdate(appCodesDTO);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> get(@PathVariable("id") long id) {
        return AppResponse.build(HttpStatus.OK).body(appCodesService.findById(id));
    }

    @GetMapping(value = "/by-type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getByType(@PathVariable("type") String type) {
        return AppResponse.build(HttpStatus.OK).body(appCodesService.findXcodeByXtype(type));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getAll() {
        return AppResponse.build(HttpStatus.OK).body(appCodesService.findAll());
    }
}
