/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.repo;

import com.rony.erpsoft.user_auth.model.Feature;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthRepo {

    @Autowired
    NamedParameterJdbcTemplate db;
    
    

    public UserInfo findUser(String usremail, String usrpkeycnv) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ui.user_id, ui.first_name, ui.last_name, ui.user_code, ui.usremail, ui.phone, ui.address, ui.country_id, ui.active, sr.role_name, sr.role_code, sr.role_id  ");
        sql.append(" FROM user_info ui  ");
        sql.append(" join user_role ur on ui.user_id = ur.user_id ");
        sql.append(" join system_role sr on sr.role_id=ur.role_id and sr.active=true ");
        sql.append(" where ui.usremail=:usremail and ui.usrpkey=:usrpkeycnv and ui.active=true  ");
        Map<String, Object> params = new HashMap<>();
        params.put("usremail", usremail);
        params.put("usrpkeycnv", usrpkeycnv);
        return (UserInfo) db.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(UserInfo.class));
    }

    public UserInfo findUserByLanId(String lanId, String usrpkeycnv) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ui.user_id, ui.first_name, ui.last_name, ui.user_code, ui.usremail, ui.lan_id, ui.phone, ui.address, ui.country_id, ui.active, sr.role_name, sr.role_code, sr.role_id  ");
        sql.append(" FROM user_info ui  ");
        sql.append(" join user_role ur on ui.user_id = ur.user_id ");
        sql.append(" join system_role sr on sr.role_id=ur.role_id and sr.active=true ");
        sql.append(" where ui.lan_id=:lanId and ui.usrpkey=:usrpkeycnv and ui.active=true  ");
        Map<String, Object> params = new HashMap<>();
        params.put("lanId", lanId);
        params.put("usrpkeycnv", usrpkeycnv);

        try {
            return (UserInfo) db.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(UserInfo.class));
        } catch (Exception ex) {
            return null;
        }
    }
    
    private Map<String, Feature> roleWiseFeature = new HashMap<String, Feature>();


    public UserLogin createLoginSessionLog(UserLogin model) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO user_logins( ");
        sql.append(" login_id, user_id, used_email, used_pwd, terminal, agent, session_start, active ) ");
        sql.append(" VALUES ( ");
        sql.append(" :login_id, :user_id, :used_email, :used_pwd, :terminal, :agent, now(), 1) ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(model);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        db.update(sql.toString(), namedParameters, keyHolder);
        model.setLogin_id(keyHolder.getKey().longValue());
        
        return model;
    }
    
    public void updateLoginSessionLog(long user_id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE user_logins set ");
        sql.append(" session_end=now() ");
        sql.append(" where user_id=:user_id and active=1 ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        db.update(sql.toString(), params);
    }
    
    public void endedLoginSessionLog(long user_id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE user_logins set ");
        sql.append(" session_end=now(), active=0 ");
        sql.append(" where user_id=:user_id and active=1 ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        db.update(sql.toString(), params);
    }
    
    public void endedLoginSessionLog(String terminal, String userAgent) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE user_logins set ");
        sql.append(" session_end=now(), active=0 ");
        sql.append(" where terminal=:terminal and active=1 and agent=:agent ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("terminal", terminal);
        params.put("agent", userAgent);
        db.update(sql.toString(), params);
    }
    
    public int getLoginSessionLog(long user_id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(login_id) cnt from user_logins ");
        sql.append(" where user_id=:user_id and active=1 ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        return db.queryForObject(sql.toString(), params, Integer.class);
    }
    
}
