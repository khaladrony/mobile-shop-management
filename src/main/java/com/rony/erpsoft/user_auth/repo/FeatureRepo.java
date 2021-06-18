/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.repo;

import com.rony.erpsoft.user_auth.model.Feature;
import com.rony.erpsoft.utils.ModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FeatureRepo implements ModelRepo<Feature> {
    @Autowired
    NamedParameterJdbcTemplate nDB;
    
    @Autowired
    JdbcTemplate db;
    
    @Override
    public List<Feature> findAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ft.feature_id, ft.feature_name, ft.feature_code, ft.note, ft.module, ft.controller, ft.action, ft.component, ft.parent_id, pf.feature_name parent_name, ft.type, ft.url, ft.is_menu, ft.need_permission, ft.sort_order, ft.active ");
        sql.append(" FROM feature ft ");
        sql.append(" left join feature pf on (ft.parent_id=pf.feature_id) ");
        sql.append(" order by ft.module, ft.controller, ft.sort_order, ft.component ");
        return nDB.query(sql.toString(), new BeanPropertyRowMapper(Feature.class));
    }
    
    
    public List<Map<String, Object>> findAllForList() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ft.feature_id, ft.feature_name, ft.feature_code, ft.note, ft.module, ft.controller, ft.action, ft.component, ft.parent_id, pf.feature_name parent_name, mf.feature_name root_module, ft.type, ft.url, ft.is_menu, ft.need_permission, ft.sort_order, ft.active ");
        sql.append(" FROM feature ft ");
        sql.append(" left join feature pf on (ft.parent_id=pf.feature_id) ");
        sql.append(" left join feature mf on (pf.parent_id=mf.feature_id) ");
        sql.append(" order by ft.module, ft.controller, ft.sort_order, ft.component ");
        return db.queryForList(sql.toString());
    }
    
    public List<Feature> findAllWithRole() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ft.feature_id, "
                + "ft.feature_name, "
                + "ft.feature_code,"
                + "ft.module, "
                + "ft.controller,"
                + "ft.action,"
                + "ft.component,  "
                +" ft.parent_id,  "
                + " ft.type,"
                + "  ft.url,"
                + " ft.is_menu, "
                + " ft.need_permission, "
                + " ft.sort_order, "
                + " ft.active , "
                + " tmp.roles ");
        sql.append(" FROM feature ft  ");
        sql.append(" join (select rf.feature_id,group_concat(sr.role_name,'##$$@@$$##')  roles ");
        sql.append(" from role_feature rf  ");
        sql.append(" join system_role sr on rf.role_id=sr.role_id ");
        sql.append(" where sr.active=true ");
        sql.append(" GROUP BY rf.feature_id ");
        sql.append(" ) as tmp on ft.feature_id=tmp.feature_id ");
        return nDB.query(sql.toString(), new RowMapper<Feature>() {
 
            public Feature mapRow(ResultSet rs, int rowNum) throws SQLException {
                Feature model = new Feature();
                model.setFeature_id(Long.parseLong(rs.getString("feature_id")));
                model.setFeature_name(rs.getString("feature_name"));
                model.setFeature_code(rs.getString("feature_code"));
                model.setModule(rs.getString("module"));
                model.setController(rs.getString("controller"));
                model.setAction(rs.getString("action"));
                model.setComponent(rs.getString("component"));
                model.setType(rs.getString("type"));
                model.setUrl(rs.getString("url"));
                model.setSort_order(rs.getInt("sort_order"));
                model.setActive(rs.getBoolean("active"));
                model.setIs_menu(rs.getBoolean("is_menu"));
                model.setNeed_permission(rs.getBoolean("need_permission"));
                String parentId=rs.getString("feature_id");
                if(parentId!=null && !parentId.trim().isEmpty()){
                    model.setParent_id(Long.parseLong(parentId));
                }
                
                String[] roles=rs.getString("roles").split("##$$@@$$##");
                for (String role : roles) {
                    model.getRoles().add(role);
                }
                
                return model;
            }
             
        });
    }
    
    public List<Feature> findByRoleName(String roleName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ft.feature_id, ft.feature_name, ft.feature_code, ft.note, ft.module, ft.controller, ft.action, ft.component,  ");
        sql.append(" ft.parent_id,  ft.type, ft.url, ft.is_menu, ft.need_permission, ft.sort_order, ft.active ,rf.is_home  ");
        sql.append(" FROM feature ft  ");
        sql.append(" join role_feature rf  on ft.feature_id =rf.feature_id ");
        sql.append(" join system_role sr on rf.role_id=sr.role_id ");
        sql.append(" where ft.active=true and  sr.active=true and sr.role_name=:role_name ");
        sql.append(" order by ft.sort_order asc, ft.module asc, ft.feature_name asc ");
        Map<String, Object> params = new HashMap<>();
        params.put("role_name", roleName);
        return nDB.query(sql.toString(), params,new BeanPropertyRowMapper(Feature.class));
    }

    @Override
    public List<Feature> findAllByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Feature findById(long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ft.feature_id, ft.feature_name, ft.feature_code, ft.note, ft.module, ft.controller, ft.action, ft.component, ft.parent_id, pf.feature_name parent_name, ft.type, ft.url, ft.is_menu, ft.need_permission, ft.sort_order, ft.active ");
        sql.append(" FROM feature ft ");
        sql.append(" left join feature pf on (ft.parent_id=pf.feature_id) ");
        sql.append(" where ft.feature_id=:feature_id ");
        Map<String, Object> params = new HashMap<>();
        params.put("feature_id", id);
        return (Feature) nDB.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(Feature.class));
    }

    @Override
    public Feature findByCode(String code) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT feature_id, feature_name, feature_code, note, module, controller, action, component, parent_id, type, url, is_menu, need_permission, sort_order, active ");
        sql.append(" FROM feature where feature_code=:feature_code ");
        Map<String, Object> params = new HashMap<>();
        params.put("feature_code", code);
        return (Feature) nDB.queryForObject(sql.toString(), params, new BeanPropertyRowMapper(Feature.class));
    }

    @Override
    public boolean save(Feature model) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO feature( ");
        sql.append(" feature_id, feature_name, feature_code, note, module, controller, action, component, parent_id, type, url, is_menu, need_permission, sort_order, ");
        sql.append(" created_by, created_on, version_no) ");
        sql.append(" VALUES ( ");
        sql.append(" :feature_id, :feature_name, :feature_code, :note, :module, :controller, :action, :component, :parent_id, :type, :url, :is_menu, :need_permission, :sort_order, ");
        sql.append(" :created_by, now(), :version_no) ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(model);
        return nDB.update(sql.toString(), namedParameters)==1;          
    }

    @Override
    public boolean update(Feature model) {

        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE feature SET  ");
        sql.append(" feature_name=:feature_name, ");
        sql.append(" note=:note, ");
        sql.append(" module=:module, ");
        sql.append(" controller=:controller, ");
        sql.append(" action=:action, ");
        sql.append(" component=:component, ");
        sql.append(" parent_id=:parent_id, ");
        sql.append(" type=:type, ");
        sql.append(" url=:url, ");
        sql.append(" is_menu=:is_menu, ");
        sql.append(" need_permission=:need_permission, ");
        sql.append(" sort_order=:sort_order, ");
        sql.append(" updated_by=:updated_by, ");
        sql.append(" version_no=:version_no, ");
        sql.append(" active=:active ");
        sql.append(" WHERE feature_id=:feature_id; ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(model);
        return nDB.update(sql.toString(), namedParameters) == 1;
    }

    @Override
    public boolean exist(Feature model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class SystemRoleRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Feature model = new Feature();
            model.setFeature_id(Long.parseLong(rs.getString("feature_id")));
            model.setFeature_name(rs.getString("feature_name"));
            model.setFeature_code(rs.getString("feature_code"));
            model.setModule(rs.getString("module"));
            model.setController(rs.getString("controller"));
            model.setAction(rs.getString("action"));
            model.setComponent(rs.getString("component"));
            model.setNote(rs.getString("note"));
            model.setSort_order(rs.getInt("sort_order"));
            model.setActive(rs.getBoolean("active"));
            return model;
        }
    }
    
    
    
    public Map<String, Object> getAppSetts() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT id, pass_exp, pass_exp_alert, sess_timeout, default_pkey ");
        sql.append(" FROM app_settings  ");
        sql.append(" where id=1 and active=1 ");
        return db.queryForMap(sql.toString());
    }
    
    
    public List<Map<String, Object>> getAppAudit(long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT aut.id, aut.change_detail, aut.exc_method, aut.session_id, aut.audit_by, aut.audit_date, aut.client_ip, aut.request_path, aut.audit_type, aut.audit_table, ");
        sql.append(" ui.user_code, ui.first_name, ui.last_name, ui.usremail, ui.phone ");
        sql.append(" FROM audit_trail aut ");
        sql.append(" LEFT JOIN user_info ui on aut.audit_by=ui.user_id ");
        sql.append(" where 1=1 ");
        
        if( userId > 0 ){
            sql.append(" and aut.audit_by=:user_id ");
        }
        
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        
        return nDB.queryForList(sql.toString(), params);
    }
    
    
    public boolean updateAppSettings(Map<String, Object> params) {

        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE app_settings SET  ");
        sql.append(" pass_exp=:pass_exp, ");
        sql.append(" pass_exp_alert=:pass_exp_alert, ");
        sql.append(" sess_timeout=:sess_timeout, ");
        sql.append(" default_pkey=:default_pkey, ");
        sql.append(" updated_by=:updated_by, ");
        sql.append(" updated_on=now() ");
        sql.append(" WHERE id=1 ");
        
        return nDB.update(sql.toString(), params) == 1;
    }
}
