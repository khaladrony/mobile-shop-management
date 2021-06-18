/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.repo;

import com.rony.erpsoft.user_auth.model.SystemRole;
import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.ModelRepo;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleRepo implements ModelRepo<SystemRole> {
    @Autowired
    NamedParameterJdbcTemplate db;
    
    @Autowired
    JdbcTemplate jdb;
    
    @Autowired
    AppUtil util;
    
    @Autowired
    SessionService sessionService;
    
    @Override
    public List<SystemRole> findAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT role_id, role_name, role_code, note, active, updated_on, created_on ");
        sql.append(" FROM system_role ");
        sql.append(" where role_name != 'SuperAdmin' order by role_name ");
        return db.query(sql.toString(), new BeanPropertyRowMapper(SystemRole.class));
    }
    
    public List<SystemRole> managerRoles() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT role_id, role_name, role_code, note, active, updated_on, created_on ");
        sql.append(" FROM system_role ");
        sql.append(" where role_name != 'SuperAdmin' order by role_name ");
        return db.query(sql.toString(), new BeanPropertyRowMapper(SystemRole.class));
    }
    
    public List<SystemRole> findAllActice() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT role_id, role_name, role_code, note, active, updated_on, created_on ");
        sql.append(" FROM system_role where active =true order by role_id  ");
        return db.query(sql.toString(), new BeanPropertyRowMapper(SystemRole.class));
    }
    
    @Override
    public List<SystemRole> findAllByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SystemRole findById(long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT role_id, role_name, role_code, note, active, updated_on, created_on ");
        sql.append(" FROM system_role where role_id=:role_id ");
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", id);
        return (SystemRole) db.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(SystemRole.class));
    }

    @Override
    public SystemRole findByCode(String code) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT role_id, role_name, role_code, note, active, updated_on, created_on ");
        sql.append(" FROM system_role where role_code=:role_code order by role_id ");
        Map<String, Object> params = new HashMap<>();
        params.put("role_code", code);
        return (SystemRole) db.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(SystemRole.class));
    }
    
    public SystemRole findByName(String name) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT role_id, role_name, role_code, note, active, created_on, updated_on ");
        sql.append(" FROM system_role where role_name=:role_name ");
        Map<String, Object> params = new HashMap<>();
        params.put("role_name", name);
        return (SystemRole) db.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(SystemRole.class));
    }
    
    @Override
    public boolean save(SystemRole model) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO system_role( ");
        sql.append(" role_id, role_name, role_code, note, ");
        sql.append(" created_by, created_on, version_no) ");
        sql.append(" VALUES ( ");
        sql.append(" :role_id, :role_name, :role_code, :note, ");
        sql.append(" :created_by, now(), :version_no ) ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(model);
        return db.update(sql.toString(), namedParameters)==1;
    }

    @Override
    public boolean update(SystemRole model) {

        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE system_role SET  ");
        sql.append(" role_code=:role_code, ");
        sql.append(" role_name=:role_name, ");
        sql.append(" note=:note, ");
        sql.append(" updated_by=:updated_by, ");
        sql.append(" updated_on=:updated_on, ");
        sql.append(" version_no=:version_no, ");
        sql.append(" active=:active ");
        sql.append(" WHERE role_id=:role_id; ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(model);
        return db.update(sql.toString(), namedParameters) == 1;
    }

    @Override
    public boolean exist(SystemRole model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public List<Map<String, Object>> findRoleFeature(long roleId) {
        StringBuilder sql = new StringBuilder();
        UserInfo ui = sessionService.getUser();
        
        sql.append(" SELECT ft.feature_id, ft.feature_name, ft.feature_code, ft.module, ft.controller, ft.parent_id, ft.action, ft.component, ft.is_menu, ft.type, coalesce(rf.is_home, false) is_home, rf.feature_id IS NOT NULL is_selected ");
        sql.append(" FROM feature ft ");
        sql.append(" left join role_feature rf on (ft.feature_id=rf.feature_id and rf.role_id=:role_id) ");
        
        if( ui.getRole_name().equalsIgnoreCase("SuperAdmin") ){
            sql.append(" where ft.active=true  ");
        } else{
            sql.append(" where ft.active=true and ft.feature_id in (select feature_id from role_feature where role_id=:access_role and active=true) ");
        }
        
        sql.append(" order by ft.parent_id ,ft.sort_order ASC ,ft.feature_name ASC ");
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", roleId);
        
        if( ui.getRole_name().equalsIgnoreCase("SuperAdmin") ){
            //
        } else{
            params.put("access_role", ui.getRole_id());
        }
        
        return db.queryForList(sql.toString(), params);
    }
    
    public List<Map<String, Object>> roleWisePermission(long roleId) {
        StringBuilder sql = new StringBuilder();
        
        sql.append(" select rf.role_id , f.feature_name, coalesce(mf.feature_name, '') module, coalesce(pf.feature_name, '') parent_feature, sr.role_name ");
        sql.append(" from role_feature rf ");
        sql.append(" join system_role sr on rf.role_id=sr.role_id ");
        sql.append(" join feature f on rf.feature_id=f.feature_id ");
        sql.append(" left join feature pf on f.parent_id=pf.feature_id ");
        sql.append(" left join feature mf on pf.parent_id=mf.feature_id ");
        sql.append(" where rf.role_id=:role_id and rf.active=1 and f.active=1 and f.controller is not null ");
        sql.append(" order by mf.feature_name ASC, f.feature_name ASC ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", roleId);
        
        return db.queryForList(sql.toString(), params);
    }
    
    public List<Map<String, Object>> roleWiseUser(long roleId) {
        StringBuilder sql = new StringBuilder();
        
        sql.append(" select ur.role_id, ui.first_name, ui.last_name, ui.usremail, ui.user_code, ui.phone, sr.role_name ");
        sql.append(" from user_role ur ");
        sql.append(" join user_info ui on ur.user_id=ui.user_id ");
        sql.append(" join system_role sr on ur.role_id=sr.role_id ");
        sql.append(" where ur.role_id=:role_id and ur.active=1 and ui.active=1 ");
        sql.append(" order by ui.first_name ASC ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", roleId);
        
        return db.queryForList(sql.toString(), params);
    }
    
    public void deleteRoleFeature(long roleId){
        StringBuilder sql = new StringBuilder();
        
        sql.append(" delete from role_feature where role_id=? ");
        jdb.update(sql.toString(), roleId);
    }
    
    public JSONArray mapRoleFeature(final long roleId, final JSONArray featureList) {
        StringBuilder sql = new StringBuilder();
        
        long maxVersion = util.getMaxVersion();
        sql.append(" insert into role_feature (role_id, feature_id, version_no) values (?,?,?) ");

        jdb.batchUpdate(sql.toString(),
        new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, roleId );
                ps.setObject(2, AppUtil.toLong(featureList.get(i)) );
                ps.setObject(3, maxVersion );
            }

            @Override
            public int getBatchSize() {
                return featureList.length();
            }
        });
        
        return featureList;
    }

    
    public boolean updateHomeFeature(long role_id, long feature_id) {

        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE role_feature SET  ");
        sql.append(" is_home=true, ");
        sql.append(" version_no=:version_no, ");
        sql.append(" updated_by=:updated_by ");
        sql.append(" WHERE role_id=:role_id and feature_id=:feature_id; ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", role_id);
        params.put("version_no", util.getMaxVersion());
        params.put("feature_id", feature_id);
        params.put("updated_by", sessionService.getUserId());
        
        return db.update(sql.toString(), params) == 1;
    }
    
}
