package com.rony.erpsoft.application_common.service;


import com.rony.erpsoft.utils.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GeneralInfoCommonService {

    @Autowired
    NamedParameterJdbcTemplate nDB;


    public Map<String, Object> getFilterData(int currentPage, int itemPerPage, Map<String, Object> params) {
        params.put("offset", (currentPage - 1) * itemPerPage);
        params.put("limit", itemPerPage);

        Map<String, Object> data = new HashMap<>();
        data.put("itemCount", count(params));
        data.put("items", rowsData(params));

        return data;
    }

    public List<Map<String, Object>> rowsData(Map<String, Object> params) {

        StringBuilder sql;
        sql = queryString(params, false);
        sql.append(params.get("sqlStringOrderBy"));

        return nDB.queryForList(sql.toString(), params);
    }

    public Long count(Map<String, Object> params) {

        StringBuilder sql;
        sql = queryString(params, true);

        return nDB.queryForObject(sql.toString(), params, Long.class);
    }

    public StringBuilder queryString(Map<String, Object> params, boolean isCount) {

        StringBuilder sql = new StringBuilder();

        if (isCount) {
            sql.append(params.get("sqlStringCount"));
        } else {
            sql.append(params.get("sqlStringRowData"));
        }

        return sql;
    }

    public String autoCodeGeneration(String prefix, int length, String lastTransactionNo) {
        int nextNumber = 1;

        if (lastTransactionNo != null && lastTransactionNo.startsWith(prefix)) {
            String numericPart = lastTransactionNo.substring(prefix.length());
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException e) {
                log.error("Class: GeneralInfoCommonService, Method: autoCodeGeneration {}", String.valueOf(e));
            }
        }

        String paddedNumber = String.format("%0" + length + "d", nextNumber);
        return prefix + paddedNumber;
    }
}
