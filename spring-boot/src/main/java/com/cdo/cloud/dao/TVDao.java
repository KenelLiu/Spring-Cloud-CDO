package com.cdo.cloud.dao;

import org.springframework.stereotype.Repository;


public interface TVDao {
	
	public void save(Integer baseId);
	
	public void delete(Integer baseId);
}
