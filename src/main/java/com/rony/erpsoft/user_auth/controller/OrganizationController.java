package com.rony.erpsoft.user_auth.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.dto.OrganizationDTO;
import com.rony.erpsoft.user_auth.service.OrganizationService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping("user_auth/organizations")
@AllArgsConstructor
public class OrganizationController extends AppProperty {

    private final OrganizationService service;
    private final AppUtil appUtil;;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("user_auth/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public AppResponse save(@RequestBody OrganizationDTO requestDTO){
        return service.createAndUpdate(requestDTO);
    }

    @PutMapping(value = "/update")
    public AppResponse update(@RequestBody OrganizationDTO requestDTO) {
        return service.createAndUpdate(requestDTO);
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getById(@PathVariable("id") long id) {
        return AppResponse.build(HttpStatus.OK).body(service.getById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getOrganization() {
        return AppResponse.build(HttpStatus.OK).body(service.getOrganization());
    }
}
