/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.repo;

import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.ModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepo implements ModelRepo<UserInfo> {
    @Autowired
    NamedParameterJdbcTemplate nDb;
    
    @Autowired
    JdbcTemplate db;
    
    @Autowired
    SessionService sessionService;
    
    @Override
    public List<UserInfo> findAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ui.user_id, ui.first_name, ui.last_name, ui.user_code, ui.usremail, ui.phone, ui.address, ui.country_id, ui.active, sr.role_name ");
        sql.append(" FROM user_info ui ");
        sql.append(" JOIN user_role ur ON (ui.user_id=ur.user_id) ");
        sql.append(" JOIN system_role sr ON (ur.role_id=sr.role_id) ");
        //sql.append(" where ui.active=true ");
        sql.append(" order by ui.user_id, ui.user_code ");
        
        Map<String, Object> params = new HashMap<>();
        return nDb.query(sql.toString(), params, new BeanPropertyRowMapper(UserInfo.class));
    }
    
    
    public List<Map<String, Object>> rowsData(Map<String, Object> params) {
        String empCode = AppUtil.toString(params.get("emp_code")).trim().toLowerCase();
        String usremail = AppUtil.toString(params.get("usremail")).trim().toLowerCase();
        String firstName = AppUtil.toString(params.get("first_name")).trim().toLowerCase();
        String lastName = AppUtil.toString(params.get("last_name")).trim().toLowerCase();
        
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ui.user_id, ui.lan_id, ui.first_name, ui.last_name, ui.user_code, ui.usremail, ui.phone, ui.address, ui.country_id, ui.active, sr.role_name ");
        sql.append(" FROM user_info ui ");
        sql.append(" JOIN user_role ur ON (ui.user_id=ur.user_id) ");
        sql.append(" JOIN system_role sr ON (ur.role_id=sr.role_id) ");
        sql.append(" WHERE ui.is_master=0 ");
        
        if( !empCode.equals("") ){
            sql.append(" AND ui.user_code=:user_code ");
            params.put("user_code", empCode );
        }
        
        if( !usremail.equals("") ){
            sql.append(" AND ui.usremail=:usremail ");
            params.put("usremail", usremail );
        }
        
        if( !firstName.equals("") ){
            sql.append(" AND lower(ui.first_name) like :firstName ");
            params.put("firstName", firstName );
        }
        
        if( !lastName.equals("") ){
            sql.append(" AND lower(ui.last_name) like :lastName ");
            params.put("lastName", lastName );
        }
        
        
        sql.append(" ORDER BY ui.user_id, ui.user_code desc limit :offset, :limit ");
        
        
        return nDb.queryForList(sql.toString(), params);
    }
    
    public Long count(Map<String, Object> params){
        String empCode = AppUtil.toString(params.get("emp_code")).trim().toLowerCase();
        String usremail = AppUtil.toString(params.get("usremail")).trim().toLowerCase();
        String firstName = AppUtil.toString(params.get("first_name")).trim().toLowerCase();
        String lastName = AppUtil.toString(params.get("last_name")).trim().toLowerCase();
        
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT count(ui.user_id) cnt ");
        sql.append(" FROM user_info ui ");
        sql.append(" JOIN user_role ur ON (ui.user_id=ur.user_id) ");
        sql.append(" JOIN system_role sr ON (ur.role_id=sr.role_id) ");
        sql.append(" WHERE ui.is_master=0 ");
        
        if( !empCode.equals("") ){
            sql.append(" AND ui.user_code=:user_code ");
            params.put("user_code", empCode );
        }
        
        if( !usremail.equals("") ){
            sql.append(" AND ui.usremail=:usremail ");
            params.put("usremail", usremail );
        }
        
        if( !firstName.equals("") ){
            sql.append(" AND lower(ui.first_name) like :firstName ");
            params.put("firstName", firstName );
        }
        
        if( !lastName.equals("") ){
            sql.append(" AND lower(ui.last_name) like :lastName ");
            params.put("lastName", lastName );
        }
        
        return nDb.queryForObject(sql.toString(), params, Long.class);
    }
    
    public List<UserInfo> userList() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ui.user_id, ui.first_name, ui.last_name, ui.user_code, ui.usremail, ui.phone, ui.address, ui.country_id, ui.active, sr.role_name ");
        sql.append(" FROM user_info ui ");
        sql.append(" JOIN user_role ur ON (ui.user_id=ur.user_id) ");
        sql.append(" JOIN system_role sr ON (ur.role_id=sr.role_id) ");
        sql.append(" where ui.active=1 ");
        sql.append(" order by ui.user_id, ui.user_code ");
        
        Map<String, Object> params = new HashMap<>();
        return nDb.query(sql.toString(), params, new BeanPropertyRowMapper(UserInfo.class));
    }

    @Override
    public List<UserInfo> findAllByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserInfo findById(long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ui.user_id, ui.first_name, ui.last_name, ui.user_code, ui.usremail, ui.lan_id, ui.phone, ui.address, ui.country_id, ui.active, sr.role_name, sr.role_id  ");
        sql.append(" FROM user_info ui  ");
        sql.append(" join user_role ur on ui.user_id = ur.user_id ");
        sql.append(" join system_role sr on sr.role_id=ur.role_id and sr.active=true ");
        sql.append(" WHERE ui.user_id=:user_id ");
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", id);
        return (UserInfo) nDb.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(UserInfo.class));
    }
    
    public int checkUserCode(String code) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(ui.user_id) cnt from user_info ui ");
        sql.append(" where ui.user_code=:user_code ");
        Map<String, Object> params = new HashMap<>();
        params.put("user_code", code);
        return nDb.queryForObject(sql.toString(), params, Integer.class);
    }
    
    public UserInfo details(long user_id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ui.user_id, ui.first_name, ui.last_name, ui.user_code, ui.usremail, ui.phone, ui.address, ui.country_id, ui.active, sr.role_name, sr.role_id  ");
        sql.append(" FROM user_info ui  ");
        sql.append(" join user_role ur on ui.user_id = ur.user_id ");
        sql.append(" join system_role sr on sr.role_id=ur.role_id and sr.active=true ");
        sql.append(" where ui.user_id=:user_id and ui.active=true ");

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        return (UserInfo) nDb.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(UserInfo.class));
    }
    
    @Override
    public UserInfo findByCode(String code) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT user_id, first_name, last_name, user_code, usremail, phone, address, country_id, active ");
        sql.append(" FROM user_info where user_code=:user_code order by sl ");
        Map<String, Object> params = new HashMap<>();
        params.put("user_code", code);
        return (UserInfo) nDb.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(UserInfo.class));
    }

    @Override
    public boolean save(UserInfo model) {
        throw new UnsupportedOperationException("Not supported yet.");      
    }
    
    public UserInfo saveUser(UserInfo model) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO user_info( ");
        sql.append(" user_id, first_name, last_name, user_code, usremail, lan_id, usrpkey, pkey_last_change, phone, address, country_id, ");
        sql.append(" created_by, created_on) ");
        sql.append(" VALUES ( ");
        sql.append(" :user_id, :first_name, :last_name, :user_code, :usremail, :lan_id, :usrpkey, now(), :phone, :address, :country_id, ");
        sql.append(" :created_by, now()) ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(model);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        nDb.update(sql.toString(), namedParameters, keyHolder);
        
        model.setUser_id( keyHolder.getKey().longValue() ); 
        
        return model;          
    }

    @Override
    public boolean update(UserInfo model) {

        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE user_info SET  ");
        sql.append(" first_name=:first_name, ");
        sql.append(" last_name=:last_name, ");
        sql.append(" usremail=:usremail, ");
        sql.append(" lan_id=:lan_id, ");
        sql.append(" phone=:phone, ");
        sql.append(" address=:address, ");
        sql.append(" updated_by=:updated_by, ");
        sql.append(" updated_on=:updated_on, ");
        sql.append(" active=:active ");
        sql.append(" WHERE user_id=:user_id; ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(model);
        return nDb.update(sql.toString(), namedParameters) == 1;
    }
    
    public boolean changePassword(long user_id, String nwPwrd, String odPwrd) {
        
        StringBuilder sql = new StringBuilder();
        sql.append(" update user_info set ");
        sql.append(" usrpkey=?, ");
        sql.append(" pkey_last_change=now(), ");
        sql.append(" password_updated_by=?, ");
        sql.append(" password_updated_on=now(), ");
        sql.append(" is_default_password_change=1 ");
        sql.append(" where user_id=? and usrpkey=? ");
        
        return db.update(sql.toString(), nwPwrd, sessionService.getUserId(), user_id, odPwrd)==1;
    }
    
    public boolean resetPassword(long user_id, String encrPsKey) {
        
        StringBuilder sql = new StringBuilder();
        sql.append(" update user_info set ");
        sql.append(" usrpkey=?, ");
        sql.append(" pkey_last_change=now(), ");
        sql.append(" password_updated_by=?, ");
        sql.append(" password_updated_on=now(), ");
        sql.append(" is_default_password_change=1 ");
        sql.append(" where user_id=? ");
        
        return db.update(sql.toString(), encrPsKey , sessionService.getUserId(), user_id ) == 1;
    }
    
    public boolean clearUserSession(long user_id) {
        
        StringBuilder sql = new StringBuilder();
        sql.append(" update user_logins set ");
        sql.append(" active=0 ");
        sql.append(" where user_id=? ");
        
        return db.update(sql.toString(), user_id) == 1;
    }

    public long mapUserRole(long user_id, long role_id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO user_role (user_id, role_id, active) ");
        sql.append(" VALUES (:user_id, :role_id, 1 ) ");
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", user_id );
        paramMap.put("role_id", role_id );
        nDb.update(sql.toString(), paramMap);
        return role_id;
    }
    
    public boolean changeStatus(long user_id, UserInfo model) {
        
        StringBuilder sql = new StringBuilder();
        sql.append(" update user_info set ");
        sql.append(" updated_by=?, ");
        sql.append(" updated_on=now(), ");
        sql.append(" active=? ");
        sql.append(" where user_id=? ");
        
        return db.update(sql.toString(), sessionService.getUserId(), model.isActive(), user_id) == 1;
    }
    
    public void revokeUserRole(long user_id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE from user_role where user_id=:user_id ");
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", user_id );
        nDb.update(sql.toString(), paramMap);
    }
    

    @Override
    public boolean exist(UserInfo model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class SystemRoleRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserInfo model = new UserInfo();
            return model;
        }
    }
    
    
    public Map<String,Object> checkUserEmail(String usremail) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(ui.user_id) cnt from user_info ui ");
        sql.append(" where ui.usremail=:usremail ");
        Map<String, Object> params = new HashMap<>();
        params.put("usremail", usremail);
        return nDb.queryForMap(sql.toString(), params);
    }

    public Map<String,Object> checkLanId(String lan_id) {
        Map<String, Object> params = new HashMap<>();
        params.put("lan_id", lan_id);
        return nDb.queryForMap(queryStringForLanId(lan_id), params);
    }

    public int checkLanIdDuplicate(String lan_id) {
        Map<String, Object> params = new HashMap<>();
        params.put("lan_id", lan_id);
        return nDb.queryForObject(queryStringForLanId(lan_id), params, Integer.class);
    }

    public String queryStringForLanId(String lan_id){
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(ui.user_id) cnt from user_info ui ");
        sql.append(" where ui.lan_id=:lan_id ");

        return sql.toString();
    }
    
    
    public Map<String,Object> getUsrSettings() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT a.pass_exp, a.pass_exp_alert, ud.cnt_day, ud.is_default_password_change ");
        sql.append(" FROM app_settings a ");
        sql.append(" JOIN( ");
        sql.append(" SELECT 1 app_id, DATEDIFF(now(), pkey_last_change) cnt_day, is_default_password_change FROM user_info WHERE user_id=:usrid ");
        sql.append(" ) as ud on a.id=ud.app_id ");
        sql.append(" WHERE id=1 ");
        Map<String, Object> params = new HashMap<>();
        params.put("usrid", sessionService.getUserId());
        return nDb.queryForMap(sql.toString(), params);
    }
}
