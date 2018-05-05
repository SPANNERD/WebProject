package com.demon.web.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import tgtools.web.platform.Platform;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author wd824
 */
@Configuration
@tk.mybatis.spring.annotation.MapperScan(basePackages = "**.dao")
public class BaseConfig {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private DataSource dataSource;

    @PostConstruct
    public void start() {
        Platform.startup(applicationContext, false, false, false, false,
                false, false);

        loadDataSource();
    }

    protected void loadDataSource()
    {
        try {
            tgtools.web.db.TransactionDataAccess dataAccess = new tgtools.web.db.TransactionDataAccess(dataSource);
            tgtools.db.DataBaseFactory.add("MyDATAACCESS", dataAccess);
            System.out.println("MyDATAACCESS: "+tgtools.db.DataBaseFactory.getDefault().getDataBaseType());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
