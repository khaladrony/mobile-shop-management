package com.rony.erpsoft.configuration;

import com.rony.erpsoft.user_auth.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sarker
 */

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    SessionService sService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try{
            if (sService.getUserId() == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return false;
            }
            boolean isViewGetReq = false;
            String uri = request.getRequestURI().toLowerCase();
            
            if( uri.contains("/view") || uri.contains(".png") || uri.contains(".jpg") || uri.contains(".jpeg") ){
                isViewGetReq = true; // if request path contains /view or .png or .jpeg or .jpg then consider as normal request
            }
            
            if( !isViewGetReq && !getTokenValue(request).equals(sService.getToken()) ){
                return false;
            }
            
        } catch(Exception ex){ }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet. - POST HANDLE"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception excptn) throws Exception {
        sService.updateSessionEnd();
    }
    
    private String getTokenValue(HttpServletRequest request) {
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if( key.equals("x-aip-token") ){
                return request.getHeader(key);
            }
        }
        return "";
    }
    
    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            map.put(key, request.getHeader(key)); // key, value pair
        }
        return map;
    }
    
}
