/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.dashboard.repo;

import com.rony.erpsoft.user_auth.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mdshahadat.sarker
 */

@Repository
public class ComponentRepo {
    
    @Autowired
    JdbcTemplate db;
    
    @Autowired
    NamedParameterJdbcTemplate nDB;
    
    @Autowired
    SessionService sessionService;
    
    public List<Map<String, Object>> getSysUserStatusCount() {
        
        StringBuilder sql = new StringBuilder();
        
        sql.append(" SELECT u.active, COUNT(u.user_id) cnt ");
        sql.append(" FROM user_info u GROUP BY u.active ");
        
        return db.queryForList(sql.toString());
    }
    
    public List<Map<String, Object>> getAtmListDetail() {
        
        StringBuilder sql = new StringBuilder();
        
        sql.append(" SELECT a.status, CASE WHEN a.status=0 THEN 'InActive' WHEN a.status =1 THEN 'Active' WHEN a.status=2 THEN 'Maintenance' END stat_name, SUM(a.cnt) cnt FROM( ");
        sql.append(" SELECT ar.status, COUNT(ar.atm_id) cnt FROM atm_registration ar GROUP BY ar.status ");
        sql.append(" UNION ALL SELECT 1 STATUS, 0 cnt ");
        sql.append(" UNION ALL SELECT 2 STATUS, 0 cnt ");
        sql.append(" UNION ALL SELECT 0 STATUS, 0 cnt ");
        sql.append(" ) AS a GROUP BY a.status order by a.status ");
        
        return db.queryForList(sql.toString());
    }
    
    public List<Map<String, Object>> getAtmFilesStats(String fromDate, String toDate) {
        
        StringBuilder sql = new StringBuilder();
        
        sql.append(" SELECT DATE_FORMAT(cal.gen_date, '%d-%b-%y') AS short_name, cal.gen_date, COALESCE(ldata.InQueue,0) InQueue, COALESCE(ldata.Downloaded,0) Downloaded, COALESCE(ldata.Partialy,0) Partialy, COALESCE(ldata.Failed,0) Failured  FROM( ");
        sql.append(" SELECT v.gen_date FROM  ");
        sql.append(" (SELECT ADDDATE('1970-01-01',t4*10000 + t3*1000 + t2*100 + t1*10 + t0) gen_date FROM ");
        sql.append(" (SELECT 0 t0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t0, ");
        sql.append(" (SELECT 0 t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1, ");
        sql.append(" (SELECT 0 t2 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2, ");
        sql.append(" (SELECT 0 t3 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3, ");
        sql.append(" (SELECT 0 t4 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t4) v ");
        sql.append(" WHERE v.gen_date BETWEEN :fromDate AND :toDate) AS cal ");
        sql.append(" LEFT JOIN ( ");
        sql.append(" SELECT ail.txn_date, SUM(CASE WHEN ail.status=-1 THEN 1 ELSE 0 END) InQueue, SUM(CASE WHEN ail.status=0 THEN 1 ELSE 0 END) Downloaded, SUM(CASE WHEN ail.status=1 THEN 1 ELSE 0 END) Partialy,SUM(CASE WHEN ail.status=2 THEN 1 ELSE 0 END) Failed ");
        sql.append(" FROM atm_image_logs ail ");
        sql.append(" WHERE ail.txn_date BETWEEN :fromDate AND :toDate GROUP BY ail.txn_date ");
        sql.append(" ) AS ldata ON cal.gen_date=ldata.txn_date ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        
        return nDB.queryForList(sql.toString(), params);
    }
    
    
}
