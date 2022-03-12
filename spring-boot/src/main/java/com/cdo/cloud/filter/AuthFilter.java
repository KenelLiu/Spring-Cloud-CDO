package com.cdo.cloud.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;

@WebFilter(filterName="authFilter",urlPatterns="/*")
@Order(2)
public class AuthFilter implements Filter{
	
	private static Log logger=LogFactory.getLog(AuthFilter.class);
	@Override
	public void destroy() {
		

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("AuthFilter............");
		HttpSession session=((HttpServletRequest)request).getSession();		
		if(session.getAttribute("user")==null){
			session.setAttribute("user", "user1");
		}
		filter.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
