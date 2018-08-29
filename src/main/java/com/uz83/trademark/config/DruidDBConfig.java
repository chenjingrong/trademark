package com.uz83.trademark.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidDBConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    private int initialSize = 5;

    private int minIdle = 5;

    private int maxActive = 20;

    private int maxWait = 60000;

    private int timeBetweenEvictionRunsMillis = 60000;

    private int minEvictableIdleTimeMillis = 300000;

    private String validationQuery = "SELECT 1";

    private boolean testWhileIdle = true;

    private boolean testOnBorrow = false;

    private boolean testOnReturn = false;

    private boolean poolPreparedStatements = true;

    private int maxPoolPreparedStatementPerConnectionSize = 20;

    private String filters = "stat,wall"; // log4j

    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000";

    @Value("${druid.userName}")
    private String adminLoginName;

    @Value("${druid.password}")
    private String adminPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
        }
        datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("resetEnable", "false");             //禁用HTML页面上的“Rest All”功能
        initParameters.put("allow", "");                         //ip白名单（没有配置或者为空，则允许所有访问）
        //initParameters.put("deny","");                          //如果某个ip同时存在，deny优先于allow
        initParameters.put("loginUsername", adminLoginName);          //++监控页面登录用户名
        initParameters.put("loginPassword", adminPassword);     //++监控页面登录用户密码
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean druidFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("druidWebStatFilter");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
