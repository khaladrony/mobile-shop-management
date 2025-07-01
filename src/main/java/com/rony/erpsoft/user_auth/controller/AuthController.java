/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.controller;


import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.model.Feature;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.repo.AuthRepo;
import com.rony.erpsoft.user_auth.repo.FeatureRepo;
import com.rony.erpsoft.user_auth.repo.OrganizationRepo;
import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sarker
 */
@RestController
public class AuthController extends AppProperty {
    
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    HttpSession session;

    @Autowired
    AuthRepo authRepo;

    @Autowired
    FeatureRepo featureRepo;

    @Autowired
    AuthService authService;

    @Autowired
    SessionService sessionService;

    @Autowired
    AppUtil appUtil;

    @Autowired
    OrganizationRepo organizationRepo;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public ModelAndView index() {
        return new ModelAndView("redirect:/auth/login");
    }

    @RequestMapping(value = {"/auth/version"}, method = {RequestMethod.GET})
    public ModelAndView version() {
        return new ModelAndView("version");
    }

    @RequestMapping(value = {"/auth/login"}, method = {RequestMethod.GET})
    public ModelAndView login() {
        appUtil.genToken();
        UserInfo userInfo = sessionService.getUser();
        ModelAndView modelAndView = null;
        
        if (userInfo != null) {
            modelAndView = new ModelAndView("redirect:/auth/launcher");
        } else {
            modelAndView = new ModelAndView("login_page");
        }
        
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
        return modelAndView;
    }

    @RequestMapping(value = {"/auth/launcher"}, method = {RequestMethod.GET})
    public ModelAndView launcher() {
        UserInfo userInfo = sessionService.getUser();
        if (userInfo != null) {
            ModelAndView modelAndView = new ModelAndView("launcher_screen");
            modelAndView.addObject(KEY.USER, userInfo);
            modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/auth/login");
        }
    }
    
    @RequestMapping(value = {"/auth/success"}, method = {RequestMethod.GET})
    public ModelAndView loginSuccess() {
        return new ModelAndView("redirect:/auth/launcher");
    }

    @RequestMapping(value = {"/auth/load"}, method = {RequestMethod.GET})
    public String load() {
        authService.loadAllFeature();
        return "OK";
    }

    @RequestMapping(value = "/auth/mydata", method = RequestMethod.GET)
    public AppResponse<UserInfo> mydata() {
        UserInfo user = sessionService.getUser();
        if (user != null) {
            user.setMenu(authService.getMenu(user.getRole_name()));

            user.setFeatures(new HashMap<String, Feature>());
            List<Feature> features = authService.getFeaturs(user.getRole_name());
            features.stream().forEach((feature) -> {
                if (feature.getType().equalsIgnoreCase("Feature")) {
                    user.getFeatures().put(feature.getModule().trim() + "##" + feature.getComponent().trim(), feature);
                } else {
                    user.getFeatures().put(feature.getFeature_id().toString(), feature);
                }
                if (feature.isIs_home()) {
                    user.setDefault_feature(featureRepo.findById(feature.getFeature_id()));
                }
            });

            return AppResponse.build(HttpStatus.OK).body(user);
        } else {
            return AppResponse.build(HttpStatus.UNAUTHORIZED).message("UserNot found");
        }
    }

    

    @RequestMapping(value = {"/auth/login"}, method = {RequestMethod.POST})
    public ModelAndView doLogin(@RequestParam("lanId") String lanId,
                                @RequestParam("usrpkeycnv") String usrpkeycnv) {

        if(lanId.trim().isEmpty()){
            return new ModelAndView("login_page").addObject("status", "User Id is required!");
        }
        
        if(usrpkeycnv.trim().isEmpty()){
            return new ModelAndView("login_page").addObject("status", "Password is required!");
        }
        
        if( !AppUtil.isValidLanId(lanId) ){
            return new ModelAndView("login_page").addObject("status", "Enter valid user id!");
        }

        /*if(!authService.licenseKeyCheck()){
            return new ModelAndView("login_page").addObject("status", "Please contact your vendor. You have a licence problem!!!");
        }*/
        
        String plainStr = appUtil.retrievePaswd(usrpkeycnv);
        
        try {
            UserInfo user = authRepo.findUserByLanId(lanId, appUtil.SHA512(plainStr));
            if (user != null) {
                
                if( !user.getRole_name().equalsIgnoreCase("SuperAdmin") && !user.getRole_name().equalsIgnoreCase("Admin") && authRepo.getLoginSessionLog(user.getUser_id()) > 0 ){
                    return new ModelAndView("login_page").addObject("status", "Please, logout from all other devices at first!");
                }
                
                try{
                    authService.createLoginSession(user, usrpkeycnv);
                } catch(Exception ex){}
                session.setAttribute(KEY.USER, user);
                session.setAttribute(KEY.ORGANIZATION, organizationRepo.findById(1));
                session.setMaxInactiveInterval(appUtil.getSessionTimeout());
                return new ModelAndView("redirect:/auth/success");
            }
        } catch (Exception e) {
            logger.info("LOGIN: " + e.getMessage());
        }
        return new ModelAndView("login_page").addObject("status", "Invalid username/password");
    }

    @RequestMapping(value = {"/auth/forget-upward"}, method = {RequestMethod.POST})
    public ModelAndView forgetPassword(@RequestParam("usremail") String usremail) {

        try {
            //emailService.sendHtmlEmail(email, "Forget password instructions for smartbilling", "this is test");
        } catch (Exception e) {
            logger.info("PWD FORGET: " + e.getMessage());
        }
        return new ModelAndView("login_page").addObject("status", "Forget passowrd instruction has been sent.");
    }

    @RequestMapping(value = {"/auth/logout"}, method = {RequestMethod.GET})
    public ModelAndView logout() {
        authService.endedLoginSession(sessionService.getUser());
        session.invalidate();
        return new ModelAndView("redirect:/auth/login");
    }

}
