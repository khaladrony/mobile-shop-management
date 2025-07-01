/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
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

    public Feature(String feature_name, String feature_code, String type) {
        this.feature_name = feature_name;
        this.feature_code = feature_code;
        this.type = type;
    }

    public boolean isIs_menu() {
        return is_menu;
    }

    public void setIs_menu(boolean is_menu) {
        this.is_menu = is_menu;
    }

    public boolean isIs_home() {
        return is_home;
    }

    public void setIs_home(boolean is_home) {
        this.is_home = is_home;
    }

    private List<Feature> features =new ArrayList<Feature>();
    private Set<String> roles =new HashSet<>();
}
