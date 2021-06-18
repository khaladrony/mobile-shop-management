package com.rony.erpsoft.dashboard.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.dashboard.service.ComponentService;
import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard/component")
public class ComponentController extends AppProperty {

    @Autowired
    SessionService sessionService;

    @Autowired
    AuthService authService;

    @Autowired
    ModelValidator modelValidator;

    @Autowired
    AppUtil appUtil;

    @Autowired
    ComponentService componentService;


    @RequestMapping(value = "/atm_list_detail", method = RequestMethod.PUT)
    public AppResponse<Object> getAtmStatList(@RequestBody Map<String, Object> params) {
        try {
            List<Map<String, Object>> list = componentService.getAtmStatList(params);
            return AppResponse.build(HttpStatus.OK).body(list);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
    
    
    @RequestMapping(value = "/system_statistics", method = RequestMethod.PUT)
    public AppResponse<Object> getSystemStatistics(@RequestBody Map<String, Object> params) {
        try {
            List<Map<String, Object>> list = componentService.getSystemStatistics(params);
            return AppResponse.build(HttpStatus.OK).body(list);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    
    @RequestMapping(value = "/atm_file_stats", method = RequestMethod.PUT)
    public AppResponse<Object> getAtmFileStatistics(@RequestBody Map<String, Object> params) {
        try {
            List<Map<String, Object>> list = componentService.getAtmFileStats(params);
            return AppResponse.build(HttpStatus.OK).body(list);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
    
}
