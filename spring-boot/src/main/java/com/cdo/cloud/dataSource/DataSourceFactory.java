package com.cdo.cloud.dataSource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceFactory {

	@Bean(name="write")
	@ConfigurationProperties("app.datasource.write")
	@Scope(scopeName=ConfigurableBeanFactory.SCOPE_SINGLETON)
	public HikariDataSource writeDataSource(){
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean(name="read")
	@ConfigurationProperties("app.datasource.read")
	@Scope(scopeName=ConfigurableBeanFactory.SCOPE_SINGLETON)
	public HikariDataSource readDataSource(){
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	

}
