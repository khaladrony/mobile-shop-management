/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.configuration;

import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.user_auth.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionCreationEvent;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author mdshahadat.sarker
 */
@Component
public class SessionsListnerService implements ApplicationListener<ApplicationEvent> {

    Logger logger = LoggerFactory.getLogger(SessionsListnerService.class);

    @Autowired
    SessionService sessionService;
    
    @Autowired
    AuthService authService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        try {
            if (applicationEvent instanceof SessionCreationEvent) {
                System.out.println("----------Session is created----------");

            } else if (applicationEvent instanceof SessionDestroyedEvent) {
                System.out.println("----------Session is destory----------");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
