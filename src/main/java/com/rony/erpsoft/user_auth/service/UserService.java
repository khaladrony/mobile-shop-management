/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.service;

import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.repo.UserRepo;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sarker
 */
@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelValidator modelValidator;

    @Autowired
    SessionService sessionService;
    
    @Autowired
    AppUtil appUtil;
    
    public List<UserInfo> findAll() {
        return userRepo.findAll();
    }
    
    public Map<String, Object> filter(int currentPage, int itemPerPage, Map<String, Object> request) throws  Exception{
        
        request.put("offset", (currentPage - 1)*itemPerPage );
        request.put("limit", itemPerPage);

        Map<String, Object> data = new HashMap<>();
        data.put("itemCount", userRepo.count(request));
        data.put("items", userRepo.rowsData(request));

        return data;
    }
    
    public List<UserInfo> userList() {
        return userRepo.userList();
    }

    public List<UserInfo> findAllByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UserInfo findById(long id) {
        return userRepo.findById(id);
    }

    public UserInfo findByCode(String code) {
        return userRepo.findByCode(code);
    }

    @Transactional
    public AppResponse<UserInfo> saveUser(UserInfo model) {

        try {
            
            if( !AppUtil.isValidEmail(model.getUsremail()) ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Enter valid email!");
            }

            if( !AppUtil.isValidLanId(model.getLan_id()) ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Enter valid user id!");
            }

            if( model.getLan_id().trim().length() > 0 && userRepo.checkLanIdDuplicate(model.getLan_id()) > 0 ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("User id should be unique!");
            }

            if( model.getUser_code().trim().length() > 0 && userRepo.checkUserCode(model.getUser_code()) > 0 ){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Employee code should be unique!");
            }
            
            model.setCreated_by(sessionService.getUserId());
            
            model.setUser_id(0);
            if (modelValidator.isValid(model)) {
                String encrPsKy = appUtil.SHA512(appUtil.getDefaultPasskey());
                model.setUsrpkey(encrPsKy);
                
                if (userRepo.saveUser(model).getUser_id() > 0) {
                    userRepo.mapUserRole(model.getUser_id(), model.getRole_id());

                    return AppResponse.build(HttpStatus.OK).body(userRepo.findById(model.getUser_id()));
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

    @Transactional
    public AppResponse<UserInfo> updateUser(UserInfo model) {

        try {
            model.setUpdated_by(sessionService.getUserId());
            
            if (modelValidator.isValid(model)) {
                if (userRepo.update(model)) {
                    userRepo.revokeUserRole(model.getUser_id());
                    userRepo.mapUserRole(model.getUser_id(), model.getRole_id());

                    return AppResponse.build(HttpStatus.OK).body(userRepo.findById(model.getUser_id()));
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
    
    public boolean clearUserSession(UserInfo model) {
        
        try {
            userRepo.clearUserSession(model.getUser_id());
            return true;
        } catch (Exception ex) {
            
        }
        
        return false;
    }
    
    public boolean resetUserPawrd(Map<String, Object> params) {
        
        try {
            
            if( !sessionService.getUser().getRole_name().equals("Admin") ){
                return false;
            }
            
            long userId = AppUtil.toLong(params.get("reseting_id"));
            int pType = AppUtil.toInt(params.get("p_type"));
            String encrPsKy = "";
            
            if( pType == 1 ){
                encrPsKy = appUtil.SHA512(appUtil.getDefaultPasskey());
            } else{
                String plainText = appUtil.retrievePaswd(AppUtil.toString(params.get("re_cnf_pwd")));
                encrPsKy = appUtil.SHA512(plainText);
            }
            
            return userRepo.resetPassword(userId, encrPsKy);
        } catch (Exception ex) {
            
        }
        
        return false;
    }
    
    public boolean changeUserPawrd(long userId, Map<String, Object> params) {
        String tmpNewPwd = String.valueOf(params.get("new_pwd"));
        String tmpOldPwd = String.valueOf(params.get("pwd"));
        
        String newPwd = appUtil.retrievePaswd(tmpNewPwd), oldPwd = appUtil.retrievePaswd(tmpOldPwd);
        
        return userRepo.changePassword(userId, appUtil.SHA512(newPwd), appUtil.SHA512(oldPwd));
    }
    
    
    @Transactional
    public AppResponse<Object> updateStatus(UserInfo model) {

        try {
            model.setUpdated_by(sessionService.getUserId());
            userRepo.changeStatus(model.getUser_id(), model);
            return AppResponse.build(HttpStatus.OK).body("success");
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
