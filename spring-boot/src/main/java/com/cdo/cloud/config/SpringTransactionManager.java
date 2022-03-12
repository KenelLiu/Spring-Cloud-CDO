package com.cdo.cloud.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.cdo.cloud.dataSource.DataSourceFactory;

@Configuration
@AutoConfigureAfter(DataSourceFactory.class)
public class SpringTransactionManager {
	@Resource(name="read")
	private DataSource readDataSource;
	
	@Resource(name="write")
	private DataSource writeDataSource;
		
	@Bean(name="txWriteManager")	
	public PlatformTransactionManager writeTransaction(){	
		//ClassPathMapperScanner
//		ServletWebServerApplicationContext
		return new DataSourceTransactionManager(writeDataSource);
	}
	
	@Bean(name="txReadManager")	
	public PlatformTransactionManager readTransaction(){
		return new DataSourceTransactionManager(writeDataSource);
	}
	
}
