/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.service;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.repo.FeatureRepo;
import com.rony.erpsoft.user_auth.repo.UserRepo;
import com.rony.erpsoft.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 *
 * @author sarker
 */
@Service
public class AppSettingService {

    @Autowired
    FeatureRepo featureRepo;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    SessionService sessionService;
    
    @Transactional
    public AppResponse<Object> updateSettingService(Map<String, Object> model) {

        try {
            
            if( !sessionService.getUser().getRole_name().equals("Admin") ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).body("You are not authorized to update!!!");
            }
            
            if( AppUtil.toInt(model.get("pass_exp")) < 1 ){
                return AppResponse.build(HttpStatus.BAD_REQUEST).body("Password expiry (in days) is required!");
            }
            
            if( AppUtil.toInt(model.get("pass_exp_alert")) < 1 ){
                return AppResponse.build(HttpStatus.BAD_REQUEST).body("Password expiry alert (in days) is required!");
            }
            
            if( AppUtil.toInt(model.get("sess_timeout")) < 1 ){
                return AppResponse.build(HttpStatus.BAD_REQUEST).body("Session timeout (in minutes) is required!");
            }
            
            if( AppUtil.toString(model.get("default_pkey")).trim().isEmpty() ){
                return AppResponse.build(HttpStatus.BAD_REQUEST).body("Default password is required!");
            }
            
            model.put("updated_by", sessionService.getUserId());
            if( featureRepo.updateAppSettings(model) ){
                return AppResponse.build(HttpStatus.OK).body("Success!");
            } else{
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).body("Unable to update settings. Try again later!!!");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
        
    }
    
    public Map<String, Object> getAppSettings(){
        Map<String, Object> datas = sessionService.getAppSettings();
        
        if( datas != null ){
            return datas;
        } else{
            datas = featureRepo.getAppSetts();
            sessionService.setAppSettings(datas);
            
            return datas;
        }
    }
    
    public Map<String, Object> getUsrSettings(){
        Map<String, Object> datas = sessionService.getUsrSettings();
        
        if( datas != null ){
            return datas;
        } else{
            datas = userRepo.getUsrSettings();
            sessionService.setUsrSettings(datas);
            
            return datas;
        }
    }
    
}
