/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.user_auth.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class SystemRole {

    @NotNull
    private long role_id;
    
    @NotNull(message = "Role name cannot be null")
    @Size(min=1, max=50, message = "Role name should be 1 to 50 chars")
    private String role_name;
    
    private String role_code;
    
    @Size(max = 200)
    private String note;
    private long created_by;
    private Date created_on;
    private long updated_by;
    private Date updated_on;
    private boolean active;
    private long version_no;
    private int sl;
}
