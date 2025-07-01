/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.service;

import com.rony.erpsoft.user_auth.model.Feature;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.model.UserLogin;
import com.rony.erpsoft.user_auth.repo.AuthRepo;
import com.rony.erpsoft.user_auth.repo.FeatureRepo;
import com.rony.erpsoft.user_auth.repo.RoleRepo;
import com.rony.erpsoft.utils.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author juba
 */
@Service
public class AuthService implements ApplicationRunner {

    @Autowired
    HttpServletRequest request;
    
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    FeatureRepo featureRepo;
    
    @Autowired
    AuthRepo authRepo;
    
    @Autowired
    SessionService sessionService;
    @Autowired
    AppUtil appUtil;

    private final Map<String, Feature> URL_FEATURES = new HashMap<String, Feature>();

    public void loadAllFeature() {
        List<Feature> features = featureRepo.findAllWithRole();
        features.stream().forEach((feature) -> {
            if (feature.getType().equals("Feature")) {
                String url = "/" + feature.getModule() + "/" + feature.getController() + "/" + feature.getAction();
                URL_FEATURES.put(url, feature);
            }
        });
    }

    public void prepareMenu(List<Feature> features, Feature parent, String roleName) {
        if (features.size() == 0) {
            return;
        }
        if (parent.getFeature_id() != null) {
            List<Feature> f = features.stream().filter(obj -> obj.getParent_id().equals(parent.getFeature_id())).collect(Collectors.toList());
            parent.setFeatures(f);
            features.removeAll(f);
            parent.getFeatures().stream().forEach((t) -> {
                prepareMenu(features, t, roleName);
            });
        } else {
            List<Feature> f = features.stream().filter(obj -> obj.getParent_id() == null).collect(Collectors.toList());
            parent.setFeatures(f);
            features.removeAll(f);
            parent.getFeatures().stream().forEach((t) -> {
                prepareMenu(features, t, roleName);
            });
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadAllFeature();
    }

    public Feature getMenu(String role) {
        Feature parent = new Feature("MENU", "0000", "ROOT");
        List<Feature> features = featureRepo.findByRoleName(role);
        prepareMenu(new ArrayList<Feature>(features), parent, role);
        return parent;
    }

    public List<Feature> getFeaturs(String role) {
        return featureRepo.findByRoleName(role);
    }
    
    public void createLoginSession(UserInfo ui, String pwd){
        UserLogin ul = new UserLogin();
        ul.setLogin_id(0L);
        ul.setUser_id(ui.getUser_id());
        ul.setUsed_email(ui.getUsremail());
        ul.setLan_id(ui.getLan_id());
        ul.setUsed_pwd(pwd);
        String terminal = "F-" + AppUtil.getHeaderInfo(request, "x-forwarded-for");
        terminal += "; R-" + AppUtil.getHeaderInfo(request, "x-real-ip");
        terminal += "; " + request.getRemoteAddr();
        ul.setTerminal(terminal);
        ul.setAgent(AppUtil.getHeaderInfo(request, "user-agent"));
        ul.setActive(true);
        
        authRepo.createLoginSessionLog(ul);
        
    }

    public void updateLoginSession(UserInfo ui){
        authRepo.updateLoginSessionLog(ui.getUser_id());
    }
    
    public void endedLoginSession(UserInfo ui){
        authRepo.endedLoginSessionLog(ui.getUser_id());
    }

    public boolean licenseKeyCheck(){

        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            String plainString = ip.getHostAddress()+"::"+ip.getHostName()+"::"+sb.toString();

            String hashString = appUtil.SHA256(plainString);
            String SecretKey = appUtil.getSecretKey();
            if(hashString.equals(SecretKey)){
                return true;
            } else {
                return false;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        }

        return false;
    }
}
