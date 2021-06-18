/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.model;

import java.sql.Date;
import java.util.Map;

public class UserInfo {
    private long user_id;
    private String full_name;
    private String first_name;
    private String last_name;
    private String user_code;
    private String usremail;
    private String lan_id;
    private String phone;
    private String usrpkey;
    private String address;
    private int country_id;
    private long created_by;
    private Date created_on;
    private long updated_by;
    private Date updated_on;
    private boolean active;
    private boolean is_master;
    private long role_id;
    private String role_name;
    private String role_code;
    private long password_updated_by;
    private Date password_updated_on;
    private boolean is_default_password_change;
    
    private Feature menu;
    private Map<String,Feature> features ;
    private Feature default_feature;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUsremail() {
        return usremail;
    }

    public void setUsremail(String usremail) {
        this.usremail = usremail;
    }

    public String getLan_id() {
        return lan_id;
    }

    public void setLan_id(String lan_id) {
        this.lan_id = lan_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsrpkey() {
        return usrpkey;
    }

    public void setUsrpkey(String usrpkey) {
        this.usrpkey = usrpkey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
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

    public boolean isIs_master() {
        return is_master;
    }

    public void setIs_master(boolean is_master) {
        this.is_master = is_master;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }

    public long getPassword_updated_by() {
        return password_updated_by;
    }

    public void setPassword_updated_by(long password_updated_by) {
        this.password_updated_by = password_updated_by;
    }

    public Date getPassword_updated_on() {
        return password_updated_on;
    }

    public void setPassword_updated_on(Date password_updated_on) {
        this.password_updated_on = password_updated_on;
    }

    public boolean isIs_default_password_change() {
        return is_default_password_change;
    }

    public void setIs_default_password_change(boolean is_default_password_change) {
        this.is_default_password_change = is_default_password_change;
    }

    public Feature getMenu() {
        return menu;
    }

    public void setMenu(Feature menu) {
        this.menu = menu;
    }

    public Map<String, Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Feature> features) {
        this.features = features;
    }

    public Feature getDefault_feature() {
        return default_feature;
    }

    public void setDefault_feature(Feature default_feature) {
        this.default_feature = default_feature;
    }
}
