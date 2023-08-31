package com.fudian.framework.jndi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * @Description 数据源配置类
 * @ClassName DataSourceConfig
 * @Author mr.hou
 * @Date 2020/6/16 5:32 下午
 * @Version 1.0.0
 **/
//@Configuration
public class DataSourceConfig {


    @Value( "${spring.datasource.jndi-name}" )
    private String jndiName;

    @Bean(name = "dataSource")
    public DataSource getDataSource() throws Exception {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource(jndiName);
    }

}
