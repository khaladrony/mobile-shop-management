package com.rony.erpsoft.dashboard.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/dashboard/statics")
public class DashboardStaticController extends AppProperty {

    @Autowired
    SessionService sessionService;

    @Autowired
    AuthService authService;

    @Autowired
    ModelValidator modelValidator;

    @Autowired
    AppUtil appUtil;

//    @Autowired
//    LogsService logsService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView("dashboard/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
        return modelAndView;
    }
    
}
