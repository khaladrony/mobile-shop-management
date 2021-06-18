/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.service;

import com.rony.erpsoft.user_auth.model.Organization;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.repo.AuthRepo;
import com.rony.erpsoft.utils.KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *
 * @author Sarker
 */
@Component
public class SessionService {

    @Autowired
    HttpSession session;
    
    @Autowired
    AuthRepo authRepo;
    
    public UserInfo getUser() {
        return (UserInfo) session.getAttribute(KEY.USER);
    }
    public Organization getOrganization() {
        return (Organization) session.getAttribute(KEY.ORGANIZATION);
    }
    
    public String getSessionId() {
        return session.getId();
    }
    
    public void setAppSettings(Map<String, Object> data) {
        session.setAttribute(KEY.APSTNGS, data);
    }
    
    public Map<String, Object> getAppSettings() {
        try{
            return (Map<String, Object>) session.getAttribute(KEY.APSTNGS);
        } catch(Exception ex){ }
        
        return null;
    }
    
    
    public void setUsrSettings(Map<String, Object> data) {
        session.setAttribute(KEY.USRTNGS, data);
    }
    
    public Map<String, Object> getUsrSettings() {
        try{
            return (Map<String, Object>) session.getAttribute(KEY.USRTNGS);
        } catch(Exception ex){ }
        
        return null;
    }

    public Long getUserId() {
        try {
            return getUser().getUser_id();
        } catch (Exception e) { }
        return null;
    }

    public Long getOrganizationId() {
        try {
            return getOrganization().getId();
        } catch (Exception e) { }
        return null;
    }
    
    public void setToken(String token){
        session.setAttribute(KEY.APPxTKN, token);
    }
    
    public String getToken(){
        return (String)session.getAttribute(KEY.APPxTKN);
    }
    
    public void updateSessionEnd(){
        try{
            if(getUserId() != null){
                authRepo.updateLoginSessionLog(getUserId());
            }
        } catch(Exception e){}
    }
    
}
