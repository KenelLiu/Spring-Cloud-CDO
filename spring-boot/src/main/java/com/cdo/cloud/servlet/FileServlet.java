package com.cdo.cloud.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FileServlet extends HttpServlet {
	private static Log logger=LogFactory.getLog(FileServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		logger.info("FileServlet......");
		ServletOutputStream os=null;
		try{
			os=resp.getOutputStream();
			os.write("success....".getBytes());
		}catch(Exception ex){
			logger.error("error:"+ex.getMessage(),ex);			
		}finally{
			if(os!=null){try{os.close();}catch(Exception ex){}}
		}
	}
}
