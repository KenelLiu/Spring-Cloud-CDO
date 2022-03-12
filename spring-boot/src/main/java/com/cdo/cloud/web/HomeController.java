package com.cdo.cloud.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdo.cloud.context.SpringContextUtil;

@RestController
public class HomeController {
	private static Log logger=LogFactory.getLog(HomeController.class);
	   @RequestMapping("/")
	   String home() {
		   	logger.info("welcome,hello word,bean="+SpringContextUtil.getBean("readJdbcTemplate").getClass());		   	
	        return "welcome,Hello World!";
	    }
}
