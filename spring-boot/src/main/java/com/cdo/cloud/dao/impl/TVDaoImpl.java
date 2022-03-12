package com.cdo.cloud.dao.impl;

import org.springframework.stereotype.Repository;

import com.cdo.cloud.dao.BaseDao;
import com.cdo.cloud.dao.TVDao;

@Repository
public class TVDaoImpl extends BaseDao implements TVDao {

	public void save(Integer id){
		logger.info(".......save......");		
		String sql="insert into Test(baseId) values (?)";
		try{
			writeJdbcTemplate.update(sql, new Object[]{id});			
//			throw new RuntimeException("save error.........");
		}finally{
//			writeJdbcTemplate.update(sql, new Object[]{id});
		}
	}
	
	public void delete(Integer id){
		logger.info(".......delete......");	
		String sql="insert into Test(baseId) values (?)";
		Integer value=Integer.valueOf(id)+1;
		try{
			writeJdbcTemplate.update(sql, new Object[]{value});
			throw new RuntimeException("delete error.........");				
		}finally{
				
		}
	}
}
