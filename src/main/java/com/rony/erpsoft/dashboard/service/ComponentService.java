/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.dashboard.service;

import com.rony.erpsoft.dashboard.repo.ComponentRepo;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mdshahadat.sarker
 */

@Service
public class ComponentService {
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    AppUtil util;
    
    @Autowired
    ComponentRepo componentRepo;
    
    int active=0, inactive=0;
    
    public List<Map<String, Object>> getSystemStatistics(Map<String, Object> request){
        List<Map<String, Object>> resp = new ArrayList<>();
        
        Map<String, Object> tmpMap = new HashMap<>();
        List<Map<String, Object>> tmpList = new ArrayList<>();
        
        // ATM machine statistics --- start
        tmpMap = new HashMap<>();
        tmpMap.put("component", "ATM Machine");
        tmpList = componentRepo.getAtmListDetail();
        active=0; inactive=0;
        tmpList.forEach(obj ->{
            if( AppUtil.toString(obj.get("stat_name")).equalsIgnoreCase("Active") ){
                active += AppUtil.toInt(obj.get("cnt"));
            } else{
                inactive += AppUtil.toInt(obj.get("cnt"));
            }
        });
        tmpMap.put("active", active);
        tmpMap.put("inactive", inactive);
        resp.add(tmpMap);
        // ATM machine statistics --- end
        
        
        // System user statistics --- start
        tmpMap = new HashMap<>();
        tmpMap.put("component", "Users");
        tmpList = componentRepo.getSysUserStatusCount();
        active=0; inactive=0;
        tmpList.forEach(obj ->{
            if( AppUtil.toBoolean(obj.get("active")) || AppUtil.toInt(obj.get("active"))==1 ){
                active += AppUtil.toInt(obj.get("cnt"));
            } else{
                inactive += AppUtil.toInt(obj.get("cnt"));
            }
        });
        tmpMap.put("active", active);
        tmpMap.put("inactive", inactive);
        resp.add(tmpMap);
        // System user statistics --- end
        
        
        
        return resp;
    }
    
    public List<Map<String, Object>> getAtmStatList(Map<String, Object> params){
        return componentRepo.getAtmListDetail();
    }
    
    
    public List<Map<String, Object>> getAtmFileStats(Map<String, Object> params){
        String fromDate = AppUtil.toString(params.get("from_date"));
        String toDate = AppUtil.toString(params.get("to_date"));
        return componentRepo.getAtmFilesStats(fromDate, toDate);
    }
    
}
