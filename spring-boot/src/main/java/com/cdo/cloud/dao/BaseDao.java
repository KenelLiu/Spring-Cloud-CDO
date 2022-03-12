package com.cdo.cloud.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {
	
	  @Resource(name="write")	
	  protected DataSource writeDataSource;
	  @Resource(name="read")
	  protected DataSource readDataSource;
	  @Resource(name="writeJdbcTemplate")
	  protected JdbcTemplate writeJdbcTemplate;
	  @Resource(name="readJdbcTemplate")
	  protected JdbcTemplate readJdbcTemplate;
	  
	  protected static Log logger=LogFactory.getLog(BaseDao.class);
	  
	  
}
