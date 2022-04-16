package com.cdo.cloud.mapper.write;

import java.util.List;

public interface BaseMapper<T> {
	public T findByKey(String code);
	
	public int add(T tlcMap);

	public int update(T tlcMap);
	
	public int deleteByKey(String code);
	
	public long getCount(T tlcMap);
	
	public List<T> getListModel(T tlcMap);
}
