/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.repo.UserRepo;
import com.rony.erpsoft.user_auth.service.UserService;
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

/**
 *
 * @author sarker
 */
@RestController
@RequestMapping("user_auth/user")
public class UserController extends AppProperty {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelValidator modelValidator;

    @Autowired
    UserService userService;

    @Autowired
    AppUtil appUtil;
    
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("user_auth/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
        return modelAndView;
    }

    @RequestMapping(value = "/filter/{currentPage}/{itemPerPage}", method = RequestMethod.POST)
    public AppResponse<Object> filter(
            @PathVariable("currentPage") int currentPage,
            @PathVariable("itemPerPage") int itemPerPage,
            @RequestBody Map<String, Object> params) {
        try {
            return AppResponse.build(HttpStatus.OK).body(userService.filter(currentPage, itemPerPage, params));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
    
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public AppResponse<List<UserInfo>> getAll() {
        List<UserInfo> models = userRepo.findAll();
        if (!models.isEmpty()) {
            return AppResponse.build(HttpStatus.OK).body(models);
        } else {
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No user found");
        }
    }
    
    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    public AppResponse<List<UserInfo>> getUserList() {
        List<UserInfo> models = userRepo.userList();
        if (!models.isEmpty()) {
            return AppResponse.build(HttpStatus.OK).body(models);
        } else {
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No user found");
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<UserInfo> get(@PathVariable("id") long id) {
        UserInfo model = userRepo.findById(id);
        if (model != null) {
            return AppResponse.build(HttpStatus.OK).body(model);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message(" User not found");
        }

    }

    @RequestMapping(value = "/clear-session", method = RequestMethod.PUT)
    public AppResponse<Object> clearSession(@RequestBody UserInfo model) {
        try{
            return AppResponse.build(HttpStatus.OK).body(userService.clearUserSession(model));
        } catch(Exception ex){
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No user updated");
        }
        
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AppResponse<UserInfo> save(@RequestBody UserInfo model) {
        return userService.saveUser(model);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public AppResponse<UserInfo> update(@PathVariable("id") long id, @RequestBody UserInfo model) {
        try{
            
            return AppResponse.build(HttpStatus.OK).body(userService.updateUser(model));
        } catch(Exception ex){
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No user updated");
        }
        
    }
    
    @RequestMapping(value = "/active_inactive/{id}", method = RequestMethod.PUT)
    public AppResponse<Object> updateStatus(@PathVariable("id") long id, @RequestBody UserInfo model) {
        try{
            return AppResponse.build(HttpStatus.OK).body(userService.updateStatus(model));
        } catch(Exception ex){
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No user updated");
        }
        
    }
    
    @RequestMapping(value = "/reset-urpawrd", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<UserInfo> resetUpaword(@RequestBody Map<String, Object> data) {
        
        try {
            if( userService.resetUserPawrd(data)){
                return AppResponse.build(HttpStatus.OK).body(1);
            } else{
                return AppResponse.build(HttpStatus.OK).body(0);
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
        
    }
    
    @RequestMapping(value = "/check-email", method = RequestMethod.PUT)
    public AppResponse<Object> checkEmail(@RequestBody Map<String, Object> model) {
        try{
            if( !AppUtil.isValidEmail(model.get("usremail").toString()) ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Enter valid email!");
            }
            return AppResponse.build(HttpStatus.OK).body(userRepo.checkUserEmail(model.get("usremail").toString()));
        } catch(Exception ex){
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No user found");
        }
        
    }

    @RequestMapping(value = "/check-lanId", method = RequestMethod.PUT)
    public AppResponse<Object> checkLanId(@RequestBody Map<String, Object> model) {
        try{
            if( !AppUtil.isValidLanId(model.get("lan_id").toString()) ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Enter valid user id!");
            }
            return AppResponse.build(HttpStatus.OK).body(userRepo.checkLanId(model.get("lan_id").toString()));
        } catch(Exception ex){
            return AppResponse.build(HttpStatus.NO_CONTENT).message("No user found");
        }

    }
}
