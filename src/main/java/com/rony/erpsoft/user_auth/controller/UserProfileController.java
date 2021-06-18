package com.rony.erpsoft.user_auth.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.repo.UserRepo;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.user_auth.service.UserService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@RestController
@RequestMapping("user_auth/profile")
public class UserProfileController extends AppProperty {
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    AppUtil appUtil;
    
    @RequestMapping(value="/view", method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        
        ModelAndView modelAndView = new ModelAndView("user_auth/view");
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/show", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<UserInfo> show() {
        UserInfo model = userRepo.details( sessionService.getUserId() );
        if (model != null) {
            return AppResponse.build(HttpStatus.OK).body(model);
        } else {
            return AppResponse.build(HttpStatus.NOT_FOUND).message(" Details not found");
        }

    }
    
    @RequestMapping(value = "/change-urpawrd", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppResponse<Object> changePawrd(@RequestBody Map<String, Object> params) {
        
        try {
            long user_id = sessionService.getUserId();
            if( userService.changeUserPawrd(user_id, params) ){
                
                return AppResponse.build(HttpStatus.OK).body(1);
            } else{
                return AppResponse.build(HttpStatus.OK).body(0);
            }    
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }

    }
    
}
