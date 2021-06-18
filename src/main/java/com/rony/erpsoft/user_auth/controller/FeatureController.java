package com.rony.erpsoft.user_auth.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.model.Feature;
import com.rony.erpsoft.user_auth.repo.FeatureRepo;
import com.rony.erpsoft.user_auth.service.AppSettingService;
import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user_auth/feature")
public class FeatureController extends AppProperty {

    @Autowired
    FeatureRepo featureRepo;

    @Autowired
    ModelValidator modelValidator;

    @Autowired
    AuthService authService;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    AppSettingService settingService;
    
    @Autowired
    AppUtil util;
    
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        util.genToken();
        ModelAndView modelAndView = new ModelAndView("user_auth/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, util.getToken());
        return modelAndView;
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public AppResponse<Object> getAll() {
        List<Map<String, Object>> features = featureRepo.findAllForList();
        if (!features.isEmpty()) {
            return AppResponse.build(HttpStatus.OK).body(features);
        } else {
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No feature found");
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Feature> get(@PathVariable("id") long id) {
        Feature feature = featureRepo.findById(id);
        if (feature != null) {
            return AppResponse.build(HttpStatus.OK).body(feature);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message(" Feature not found");
        }

    }
    
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<Feature> save(@RequestBody Feature model) {
        model.setFeature_id(0L);
        model.setCreated_by(sessionService.getUserId());
        model.setUpdated_by(sessionService.getUserId());
        model.setVersion_no(util.getMaxVersion());
        
        try {
            if (modelValidator.isValid(model)) {
                if (featureRepo.save(model)) {
                    authService.loadAllFeature();
                    return AppResponse.build(HttpStatus.OK).body(model);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(model));
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public AppResponse<Feature> update(@PathVariable("id") long id, @RequestBody Feature model) {

        try {
            model.setCreated_by(sessionService.getUserId());
            model.setUpdated_by(sessionService.getUserId());
            model.setVersion_no(util.getMaxVersion());
        
            if (modelValidator.isValid(model)) {
                if (featureRepo.update(model)) {
                    authService.loadAllFeature();
                    return AppResponse.build(HttpStatus.OK).body(featureRepo.findById(model.getFeature_id()));
                } else {
                    return AppResponse.build(HttpStatus.NOT_MODIFIED).message("Not updated");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(model));
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
    
    
    @RequestMapping(value = "/getAppSetts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getAppSetts() {
        
        Map<String, Object> data = featureRepo.getAppSetts();
        
        if (data != null) {
            return AppResponse.build(HttpStatus.OK).body(data);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message(" Feature not found");
        }

    }
    
    @RequestMapping(value = "/getAppAudit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> getAppAudit(@RequestBody Map<String, Object> params) {
        long userId = AppUtil.toLong(params.get("user_id"));
        
        List<Map<String, Object>> list = featureRepo.getAppAudit(userId);
        
        if (list != null && !list.isEmpty() ) {
            return AppResponse.build(HttpStatus.OK).body(list);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message(" Audit data not found");
        }

    }
    
    @RequestMapping(value = "/audit_download/{userId}", method = RequestMethod.GET)
    public ModelAndView auditDownload(@PathVariable("userId") long userId){
        ModelAndView modelAndView = new ModelAndView("user_auth/user_wise_audit");
        List<Map<String, Object>> list = featureRepo.getAppAudit(userId);
        modelAndView.addObject("data", list);
        return modelAndView;
    }
    
    @RequestMapping(value = "/saveAppSetts", method = RequestMethod.PUT)
    public AppResponse<Object> saveAppSettings(@RequestBody Map<String, Object> model) {
        return settingService.updateSettingService(model);
    }
}
