package com.cdo.boot;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
public class BootApplication {
	private static Log logger=LogFactory.getLog(BootApplication.class);
	   @RequestMapping("/")
	    String home() {
		   	logger.info("hello word");
	        return "Hello World!";
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(BootApplication.class, args);
	    }
}
