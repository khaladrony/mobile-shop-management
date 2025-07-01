/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.configuration;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 *
 * @author mdshahadat.sarker
 */
//@Configuration
//public class DbConfig {
//
//    @Bean(name = "msWebDB")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.dsweb")
//    public DataSource mysqlHealthRohingyaDataSource() {
//            return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "mysqlWebJdbcTemplate")
//    public JdbcTemplate mysqlWebJdbcTemplate(@Qualifier("msWebDB") DataSource dsCustomMySQL) {
//            return new JdbcTemplate(dsCustomMySQL);
//    }
//
//    @Bean(name = "namedWebJdbcTemplate")
//    public NamedParameterJdbcTemplate namedSchJdbcTemplate(@Qualifier("msWebDB") DataSource dsCustomMySQL) {
//            return new NamedParameterJdbcTemplate(dsCustomMySQL);
//    }
//}

