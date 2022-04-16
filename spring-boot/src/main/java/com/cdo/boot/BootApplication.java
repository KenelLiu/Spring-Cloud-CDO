package com.cdo.boot;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
@ComponentScan(basePackages={"com.iot.bodyiot"})
@ServletComponentScan(basePackages={"com.iot.bodyiot.filter"})
public class BootApplication {
	private static Log logger=LogFactory.getLog(BootApplication.class);
	
	public static void main(String[] args) {
	        SpringApplication.run(BootApplication.class, args);
	        logger.info("============启动完成========");
	}
}
