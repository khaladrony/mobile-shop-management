/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Map;

@Setter
@Getter
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

    public boolean isIs_master() {
        return is_master;
    }

    public void setIs_master(boolean is_master) {
        this.is_master = is_master;
    }

    public boolean isIs_default_password_change() {
        return is_default_password_change;
    }

    public void setIs_default_password_change(boolean is_default_password_change) {
        this.is_default_password_change = is_default_password_change;
    }
}
