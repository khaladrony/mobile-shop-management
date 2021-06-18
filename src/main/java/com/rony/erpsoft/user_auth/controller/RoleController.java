package com.rony.erpsoft.user_auth.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.model.SystemRole;
import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.user_auth.service.RoleService;
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
@RequestMapping("user_auth/role")
public class RoleController extends AppProperty {

    @Autowired
    ModelValidator modelValidator;
    
    @Autowired
    AuthService authService;
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    AppUtil appUtil;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("user_auth/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
        return modelAndView;
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public AppResponse<List<SystemRole>> getAll() {
        List<SystemRole> roles = roleService.findAll();
        if (!roles.isEmpty()) {
            return AppResponse.build(HttpStatus.OK).body(roles);
        } else {
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No role found");
        }
    }
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<SystemRole> get(@PathVariable("id") long id) {
        SystemRole model = roleService.findById(id);
        if (model != null) {
            return AppResponse.build(HttpStatus.OK).body(model);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message(" Role not found");
        }

    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<SystemRole> save(@RequestBody SystemRole model) {
        model.setRole_id(0);
        try {
            SystemRole pre = roleService.findByName(model.getRole_name());
            if( pre != null && pre.getRole_name().equalsIgnoreCase(model.getRole_name()) ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Role has already been exist!");
            }
            
            if (modelValidator.isValid(model)) {
                if (roleService.save(model)) {
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
    public AppResponse<SystemRole> update(@PathVariable("id") long id, @RequestBody SystemRole model) {

        try {
            SystemRole pre = roleService.findByName(model.getRole_name());
            if( pre != null && pre.getRole_name().equalsIgnoreCase(model.getRole_name()) && pre.getRole_id() != model.getRole_id() ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Role has already been exist!");
            }
            
            if (modelValidator.isValid(model)) {
                if (roleService.update(model)) {
                    authService.loadAllFeature();
                    return AppResponse.build(HttpStatus.OK).body(roleService.findById(model.getRole_id()));
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
    
    @RequestMapping(value = "/features/{role_id}", method = RequestMethod.GET)
    public AppResponse<Object> roleFeature(@PathVariable("role_id") long role_id) {

        try {
            return AppResponse.build(HttpStatus.OK).body(roleService.findRoleFeature(role_id));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
    
    @RequestMapping(value = "/mapRoleFeatures", method = RequestMethod.POST)
    public AppResponse<Object> mapRoleFeature(@RequestBody Map<String, Object> roleFeature) {

        try {
            Object obj = roleService.mapRoleFeatures(roleFeature);
            authService.loadAllFeature();
            return AppResponse.build(HttpStatus.OK).body(obj);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
    
    
    @RequestMapping(value = "/roleWisePermission/{rlId}", method = RequestMethod.GET)
    public ModelAndView roleWisePermission(@PathVariable("rlId") long rlId){
        ModelAndView modelAndView = new ModelAndView("user_auth/role_wise_permission");
        List<Map<String, Object>> list = roleService.roleWisePermission(rlId);
        modelAndView.addObject("data", list);
        return modelAndView;
    }
    
    @RequestMapping(value = "/roleWiseUser/{rlId}", method = RequestMethod.GET)
    public ModelAndView roleWiseUser(@PathVariable("rlId") long rlId){
        ModelAndView modelAndView = new ModelAndView("user_auth/role_wise_user");
        List<Map<String, Object>> list = roleService.roleWiseUser(rlId);
        modelAndView.addObject("data", list);
        return modelAndView;
    }
}
