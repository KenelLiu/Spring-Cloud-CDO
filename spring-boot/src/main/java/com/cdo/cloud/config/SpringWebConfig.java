package com.cdo.cloud.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cdo.cloud.filter.AuthFilter;
import com.cdo.cloud.filter.OrderFilter;
import com.cdo.cloud.listener.AppListener;
import com.cdo.cloud.listener.SessionListener;
import com.cdo.cloud.servlet.FileServlet;

@Configuration
public class SpringWebConfig  implements WebMvcConfigurer {
	/**
	@Bean
	public FilterRegistrationBean<Filter> getOrderFilter(){
		OrderFilter filter=new OrderFilter();
		FilterRegistrationBean<Filter> registrationBean=new FilterRegistrationBean<Filter>();
		registrationBean.setFilter(filter);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(1);
		return registrationBean;
	}	
	
	@Bean
	public FilterRegistrationBean<Filter> getAuthFilter(){		
		AuthFilter filter=new AuthFilter();
		FilterRegistrationBean<Filter> registrationBean=new FilterRegistrationBean<Filter>();
		registrationBean.setFilter(filter);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(2);
		return registrationBean;
	}
	**/
	@Bean
	public ServletRegistrationBean<HttpServlet> getFileServlet(){
		FileServlet servlet=new FileServlet();		
		ServletRegistrationBean<HttpServlet> registrationBean=new ServletRegistrationBean<HttpServlet>();
		registrationBean.setServlet(servlet);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/servlet/file/*");//拦截路径，可以添加多个
		registrationBean.setUrlMappings(urlPatterns);
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
	@Bean
	public ServletListenerRegistrationBean<ServletContextListener>  getListener(){
		AppListener listener=new AppListener();
		ServletListenerRegistrationBean<ServletContextListener> registrationBean=new ServletListenerRegistrationBean<ServletContextListener>();
		registrationBean.setEnabled(true);
		registrationBean.setListener(listener);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionListener>  getHttpListener(){
		SessionListener listener=new SessionListener();
		ServletListenerRegistrationBean<HttpSessionListener>  registrationBean=new ServletListenerRegistrationBean<HttpSessionListener> ();
		registrationBean.setEnabled(true);
		registrationBean.setListener(listener);
		return registrationBean;
	}	
}
	
