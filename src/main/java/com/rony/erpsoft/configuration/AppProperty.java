/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.configuration;

import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.service.AppSettingService;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *
 * @author sarker
 */
public class AppProperty {
    
    @Autowired
    AppUtil util;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    AppSettingService appeSettingService;
    
    @ModelAttribute
    public void addCommonObjects(Model model, HttpServletRequest request, HttpServletResponse resp, HttpSession httpSession){
        model.addAttribute("BASE_URL", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        model.addAttribute("STATIC_RES", request.getContextPath() + "/res");
        model.addAttribute(KEY.JSPVIEWKEY, util.getToken());
        model.addAttribute("ANGULAR", request.getContextPath() + "/res/angular");
        model.addAttribute("NG_SRC", request.getContextPath() + "/view");
        model.addAttribute("APP", request.getContextPath());
        model.addAttribute("APP_NAME", util.getAppName());
        model.addAttribute("APP_FULL_NAME", util.getAppFullName());
        model.addAttribute(KEY.SESSION_TIMEOUT, (util.getSessionTimeout()*1000)+1500); // convert seconds to mi
        model.addAttribute("APP_VERSION", "1.0.1");
        model.addAttribute("SCRIPT_VERSION", "1.0.1"); // should be app version. please change before release
        
        UserInfo ui = sessionService.getUser();
        if( ui != null ){
            model.addAttribute(KEY.USER, ui);
            model.addAttribute("ROLE_CODE", ui.getRole_code());
            model.addAttribute("ROLE_NAME", ui.getRole_name());
            model.addAttribute("USER_ID", ui.getUser_id());
            model.addAttribute("USER_CODE", ui.getUser_code());
            
            Map<String, Object> sets = appeSettingService.getUsrSettings();
            
            model.addAttribute("SHOW_ALERT", ( AppUtil.toInt(sets.get("cnt_day")) > (AppUtil.toInt(sets.get("pass_exp"))-AppUtil.toInt(sets.get("pass_exp_alert"))) )? 1 : 0 );
            model.addAttribute("FORCE_CHANGE", ( AppUtil.toInt(sets.get("cnt_day")) > AppUtil.toInt(sets.get("pass_exp")) )? 1 : 0 );
            model.addAttribute("FORCE_CHANGE_DEFAULT_PASSWORD", (AppUtil.toBoolean(sets.get("is_default_password_change"))) ? 0 : 1);
        }
        
    }
}
