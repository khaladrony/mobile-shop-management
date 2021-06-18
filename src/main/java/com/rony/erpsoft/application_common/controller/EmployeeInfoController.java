package com.rony.erpsoft.application_common.controller;

import com.rony.erpsoft.application_common.actionService.CustomerInfoActionService;
import com.rony.erpsoft.application_common.actionService.EmployeeInfoActionService;
import com.rony.erpsoft.application_common.model.BankInfo;
import com.rony.erpsoft.application_common.model.EmployeeInfo;
import com.rony.erpsoft.application_common.service.CustomerInfoService;
import com.rony.erpsoft.application_common.service.EmployeeInfoService;
import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/application_common/employee_info")
public class EmployeeInfoController extends AppProperty {

    @Autowired
    AppUtil appUtil;
    @Autowired
    EmployeeInfoActionService employeeInfoActionService;
    @Autowired
    EmployeeInfoService employeeInfoService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("application_common/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<BankInfo> save(@RequestBody EmployeeInfo employeeInfo){
        return employeeInfoActionService.execute(employeeInfo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public AppResponse<BankInfo> update(@RequestBody EmployeeInfo employeeInfo) {
        return employeeInfoActionService.execute(employeeInfo);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> get(@PathVariable("id") long id) {
        EmployeeInfo model = employeeInfoService.findById(id);
        if (model != null) {
            return AppResponse.build(HttpStatus.OK).body(model);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message("Voucher not found");
        }
    }

    @RequestMapping(value = "/filter/{currentPage}/{itemPerPage}", method = RequestMethod.POST)
    public AppResponse<Object> filter(
            @PathVariable("currentPage") int currentPage,
            @PathVariable("itemPerPage") int itemPerPage,
            @RequestBody Map<String, Object> params) {
        try {
            return AppResponse.build(HttpStatus.OK).body(employeeInfoService.filter(currentPage, itemPerPage, params));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/get/list", method = RequestMethod.GET)
    public AppResponse<Object> getEmployeeList() {
        try {
            List<Map<String, Object>> list = employeeInfoService.getEmployeeListForDropDown();
            if (!list.isEmpty()) {
                return AppResponse.build(HttpStatus.OK).body(list);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Employee not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
