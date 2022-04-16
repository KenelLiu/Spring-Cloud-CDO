package com.cdo.cloud.dataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@AutoConfigureAfter(DataSourceFactory.class)  
//@MapperScan(basePackages={"com.cdo.cloud.mapper"})
public class MybatisSessionFactory {
	  @Resource(name="read")
	  private DataSource readDataSource;
	  
	  @Resource(name="write")	
	  private DataSource writeDataSource;
	  
	  @Bean(name="readFactory")
	  public SqlSessionFactory readSessionFactory() throws Exception {
	        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	        factoryBean.setDataSource(readDataSource);
	        return factoryBean.getObject();
	    }

	  @Bean(name="readTemplate")
	  public SqlSessionTemplate readSessionTemplate() throws Exception {
	        SqlSessionTemplate template = new SqlSessionTemplate(readSessionFactory()); // 使用上面配置的Factory	        
	        return template;
	    }
	  
	  @Bean(name="writeFactory")
	  public SqlSessionFactory writeSessionFactory() throws Exception {
	        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	        factoryBean.setDataSource(writeDataSource);
	        org.springframework.core.io.Resource[] resources=resolveMapperLocations();
	        if(resources.length>0)
	        	factoryBean.setMapperLocations(resources);
	        return factoryBean.getObject();
	    }

	  @Bean(name="writeTemplate")
	  public SqlSessionTemplate writeSessionTemplate() throws Exception {
	        SqlSessionTemplate template = new SqlSessionTemplate(writeSessionFactory()); // 使用上面配置的Factory
	        return template;
	    }	
	  
	  @Bean(name="readJdbcTemplate")
	  public JdbcTemplate readJdbcTemplate() throws Exception {
		  	JdbcTemplate template = new JdbcTemplate(readDataSource); 
	        return template;
	    }
	  
	  @Bean(name="writeJdbcTemplate")
	  public JdbcTemplate writeJdbcTemplate() throws Exception {
		  	JdbcTemplate template = new JdbcTemplate(writeDataSource); 
	        return template;
	    }	  
	
	  private org.springframework.core.io.Resource[] resolveMapperLocations(){
			  ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
			  List<String> mapperLocations=new ArrayList<>();
			  mapperLocations.add("classpath*:com/cdo/cloud/mapper/xml/*Mapper*.xml");
			  List<org.springframework.core.io.Resource> resources=new ArrayList<>();
			  for (String mapperLocation : mapperLocations){
				try{
					org.springframework.core.io.Resource[] mappers=resolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				}catch(IOException ex){
					ex.printStackTrace();
				}
			 }
			 return resources.toArray(new org.springframework.core.io.Resource[resources.size()]);
		  }
}
