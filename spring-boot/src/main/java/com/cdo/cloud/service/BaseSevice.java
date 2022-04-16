package com.cdo.cloud.service;

import java.util.List;
import com.cdo.cloud.exception.BussinessException;

public interface BaseSevice<T> {
	public T findByKey(String code);
	
	public int add(T tlcMap) throws BussinessException; 
	
	public int update(T tlcMap) throws BussinessException; 
	/**
	 * 
	 * @param tlcMap
	 * @param bQuery 是否先查询数据，进行判断,再更新数据
	 * @return
	 * @throws BussinessException
	 */
	public int update(T tlcMap,boolean bQuery) throws BussinessException; 
	
	public int deleteByKey(String code);
	
	public long getCount(T tlcMap);
	
	public List<T> getListModel(T tlcMap);
}
