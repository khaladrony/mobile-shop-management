/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Feature {

    @NotNull
    private Long feature_id;
    @NotNull
    private String feature_name;
    private String feature_code;
    @Size(max = 200)
    private String note;
    private String module;
    private String controller;
    private String action;
    private String component;
    private Long parent_id;
    private String parent_name;
    private String type;
    private String url;
    private boolean is_menu;
    private boolean need_permission;
    private int sort_order;
    private long created_by;
    private Date created_on;
    private long updated_by;
    private Date updated_on;
    private boolean active;
    private long version_no;
    private boolean is_home;
    
    public  Feature(){}
    public Feature(String feature_name, String feature_code, String type) {
        this.feature_name = feature_name;
        this.feature_code = feature_code;
        this.type = type;
    }

    public Long getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(Long feature_id) {
        this.feature_id = feature_id;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public String getFeature_code() {
        return feature_code;
    }

    public void setFeature_code(String feature_code) {
        this.feature_code = feature_code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIs_menu() {
        return is_menu;
    }

    public void setIs_menu(boolean is_menu) {
        this.is_menu = is_menu;
    }

    public boolean isNeed_permission() {
        return need_permission;
    }

    public void setNeed_permission(boolean need_permission) {
        this.need_permission = need_permission;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(long created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(long updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getVersion_no() {
        return version_no;
    }

    public void setVersion_no(long version_no) {
        this.version_no = version_no;
    }

    public boolean isIs_home() {
        return is_home;
    }

    public void setIs_home(boolean is_home) {
        this.is_home = is_home;
    }
    
    
    private List<Feature> features =new ArrayList<Feature>();
    private Set<String> roles =new HashSet<>();
    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
