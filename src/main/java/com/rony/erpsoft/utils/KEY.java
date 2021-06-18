/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.utils;

/**
 *
 * @author juba
 */
public class KEY {
    public static final String AES_SECRET="sKey43Sx5@cRetk4";
    public static final String USER="USER";
    public static final String ORGANIZATION="ORGANIZATION";
    public static final String APSTNGS="APSTNGS";
    public static final String USRTNGS="USRTNGS";
    
    public static final String user_id="user_id";
    public static final String login_id="login_id";
    public static final String note="note";
    public static final String type="type";
    public static final String is_selected="is_selected";
    public static final String active="active";
    public static final String APPxTKN="APPxTKN";
    public static final String JSPVIEWKEY="XATKN";
    public static final String REPORT_TYPE="REPORT_TYPE";
    
    public static final String JVM_MAX_MEM="JVM_MAX_MEM";
    public static final String JVM_USE_MEM="JVM_USE_MEM";
    public static final String JVM_GC_MEM="JVM_GC_MEM";
    public static final String SYS_CPU_AVL="SYS_CPU_AVL";
    public static final String SYS_CPU_USE="SYS_CPU_USE";
    public static final String PROCESS_UPTIME="PROCESS_UPTIME";
    public static final String SESSION_TIMEOUT="SESSION_TIMEOUT";
    
    public class TEMPLATE_FOR{
        public static final String NEW_USER="NEW_USER";
        public static final String UPDATE_USER="UPDATE_USER";
        public static final String CHANGE_PASSWORD="CHANGE_PASSWORD";
    }
    
    public class TEMPLATE_TYPE{
        public static final String EMAIL="EMAIL";
        public static final String SMS="SMS";
        public static final String FCM="FCM";
    }
    
    public class URL{
        public static final String JVM_MAX_MEM="/metrics/jvm.memory.max";
        public static final String JVM_USE_MEM="/metrics/jvm.memory.used";
        public static final String JVM_GC_MEM="/metrics/jvm.gc.memory.allocated";
        public static final String SYS_CPU_AVL="/metrics/system.cpu.count";
        public static final String SYS_CPU_USE="/metrics/system.cpu.usage";
        public static final String PROCESS_UPTIME="/metrics/process.uptime";
    }
    
}
