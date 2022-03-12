package com.cdo.cloud.mapper.write;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;


public interface ConfigMapper {
	@Insert(value={"insert into Test(baseId) values(#{baseId})"})	
	public void save(@Param("baseId") Integer baseId);
	

	
	
}


