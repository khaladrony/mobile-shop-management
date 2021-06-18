/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.model;


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

    public long getLogin_id() {
        return login_id;
    }

    public void setLogin_id(long login_id) {
        this.login_id = login_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsed_email() {
        return used_email;
    }

    public void setUsed_email(String used_email) {
        this.used_email = used_email;
    }

    public String getLan_id() {
        return lan_id;
    }

    public void setLan_id(String lan_id) {
        this.lan_id = lan_id;
    }

    public String getUsed_pwd() {
        return used_pwd;
    }

    public void setUsed_pwd(String used_pwd) {
        this.used_pwd = used_pwd;
    }

    public String getSession_start() {
        return session_start;
    }

    public void setSession_start(String session_start) {
        this.session_start = session_start;
    }

    public String getSession_end() {
        return session_end;
    }

    public void setSession_end(String session_end) {
        this.session_end = session_end;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
