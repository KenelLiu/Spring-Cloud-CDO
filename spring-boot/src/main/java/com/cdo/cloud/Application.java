package com.cdo.cloud;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.cdo.cloud.context.SpringContextUtil;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
@ComponentScan(basePackages={"com.cdo.cloud"})
@ServletComponentScan(basePackages={"com.cdo.cloud.filter"})
public class Application {
	private static Log logger=LogFactory.getLog(Application.class);
	
	public static void main(String[] args) throws FileNotFoundException, IOException {	   	   		
		ApplicationContext applicationContext= SpringApplication.run(Application.class, args);
		new SpringContextUtil().setApplicationContext(applicationContext);
		logger.info("============启动完成========"+logger.getClass());
	}
	/**
	public static void main(String[] args) throws FileNotFoundException, IOException {	   	   		
		String springConfig=System.getProperty("SPRING_CONFIG_FILE");
		File file=new File(springConfig);		
		SpringApplication app=new SpringApplication(Application.class);
		Properties prop=new Properties();
		prop.load(new FileInputStream(file));
		prop.setProperty("logging.config", file.getParent()+"/"+prop.get("logging.config"));
		app.setDefaultProperties(prop);			
		app.run(args);		
	}
	**/
}
