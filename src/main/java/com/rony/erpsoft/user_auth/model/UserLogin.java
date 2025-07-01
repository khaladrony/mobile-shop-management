/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLogin {
    private long login_id;
    private long user_id;
    
    private String used_email;
    private String lan_id;
    private String used_pwd;
    private String session_start;
    private String session_end;
    private String agent;
    private String terminal;
    
    private boolean active;
}
