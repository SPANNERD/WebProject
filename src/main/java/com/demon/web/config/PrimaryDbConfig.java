package com.demon.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import tgtools.web.develop.mybatis.interceptor.DataTableInterceptor;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author wd824
 */
@Configuration
public class PrimaryDbConfig {

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("loginUsername", "admin");
        registrationBean.addInitParameter("loginPassword", "123456");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean druidWebStatViewFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter());
        registrationBean.addInitParameter("urlPatterns", "/*");
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return registrationBean;
    }

    @Bean
    @Primary
    public DataSource dataSource(@Value("${spring.datasource.base.url}") String url,
                                 @Value("${spring.datasource.base.driverClassName}") String driver,
                                 @Value("${spring.datasource.base.username}") String userName,
                                 @Value("${spring.datasource.base.password}") String password,
                                 @Value("${spring.datasource.base.maxActive}") int maxActive,
                                 @Value("${spring.datasource.base.filters}") String filters,
                                 @Value("${spring.datasource.base.initialSize}")
                                         int initialSize,
                                 @Value("${spring.datasource.base.minIdle}")
                                         int minIdle,
                                 @Value("${spring.datasource.base.maxWait}")
                                         int maxWait,
                                 @Value("${spring.datasource.base.timeBetweenEvictionRunsMillis}")
                                         int timeBetweenEvictionRunsMillis,
                                 @Value("${spring.datasource.base.minEvictableIdleTimeMillis}")
                                         int minEvictableIdleTimeMillis,
                                 @Value("${spring.datasource.base.validationQuery}")
                                         String validationQuery,
                                 @Value("${spring.datasource.base.testWhileIdle}")
                                         boolean testWhileIdle,
                                 @Value("${spring.datasource.base.testOnBorrow}")
                                         boolean testOnBorrow,
                                 @Value("${spring.datasource.base.testOnReturn}")
                                         boolean testOnReturn,
                                 @Value("${spring.datasource.base.poolPreparedStatements}")
                                         boolean poolPreparedStatements,
                                 @Value("${spring.datasource.base.maxPoolPreparedStatementPerConnectionSize}")
                                         int maxPoolPreparedStatementPerConnectionSize,
                                 @Value("${spring.datasource.base.connectionProperties}")
                                         String connectionProperties,
                                 @Value("${spring.datasource.base.useGlobalDataSourceStat}")
                                         boolean useGlobalDataSourceStat

    ) {
        DruidDataSource dataSource = new DruidDataSource();
        /*数据源主要配置*/
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        /*数据源补充配置*/
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        dataSource.setConnectionProperties(connectionProperties);
        dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);

        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;

    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("dataSource") DataSource primaryDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(false);
        sessionFactory.setConfiguration(config);
        sessionFactory.setPlugins(new Interceptor[]{new DataTableInterceptor()});
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory")
                                                                SqlSessionFactory sqlSessionFactory1) {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory1);
        return template;
    }

    @Bean
    public tk.mybatis.spring.mapper.MapperScannerConfigurer mapperScannerConfigurer1()
    {
        tk.mybatis.spring.mapper.MapperScannerConfigurer dd =new tk.mybatis.spring.mapper.MapperScannerConfigurer();
        dd.setBasePackage("**.dao.base");
        dd.setSqlSessionFactoryBeanName("primarySqlSessionFactory");
        return dd;
    }
}
