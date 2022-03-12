package com.cdo.cloud.web;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cdo.cloud.context.SpringContextUtil;
import com.cdo.cloud.service.ConfigService;
import com.cdo.cloud.service.TVService;

@RestController
@RequestMapping("/v1")
public class WebController {
	
	private static Log logger=LogFactory.getLog(WebController.class);

	@Autowired  
    private Environment env;  
	
	@Resource(name="TVService")
	private TVService tvService;

	@Resource(name="ConfigService")
	private ConfigService configService;	
	

	@RequestMapping(path="/",method={RequestMethod.GET,RequestMethod.POST})
	public String home(){
		logger.info("request /home ");
		return "home";
	}
	/**
	 * content-Type:application/json
	 * url=http://ip:port/v1/put/2
	 * body:{"key":"val1"}
	 * @param id
	 * @param body
	 * @return
	 */
	@RequestMapping(path="/put/{id}",method={RequestMethod.POST},produces={"application/json;charset=utf-8"})
	public String json(@PathVariable String id,@RequestBody String body){		
		logger.info("request,id="+id+",body="+body);
		logger.info("request,env="+env.getProperty("spring.application.name"));
		JSONObject json=new JSONObject();
		json.put("return", "value");
		
//		tvService.save(Integer.valueOf(id));		
		/**
		DataSource dataSource=(DataSource)(SpringContextUtil.getBean("read"));
		Connection conn=null;
		try{
			conn=DataSourceUtils.getConnection(dataSource);
			logger.info("conn="+conn);
		}finally{
			try{conn.close();}catch(Exception ex){}
		}
		**/
		return json.toString();
	}
	

	
}
 
