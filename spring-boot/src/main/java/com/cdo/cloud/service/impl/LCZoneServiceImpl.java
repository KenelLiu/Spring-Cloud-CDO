package com.cdo.cloud.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cdo.cloud.entity.TLcZone;
import com.cdo.cloud.exception.BussinessException;
import com.cdo.cloud.mapper.write.LCZoneMapper;
import com.cdo.cloud.service.LCZoneService;

@Service(value="LCZoneService")
public class LCZoneServiceImpl implements LCZoneService{
	@Resource
	LCZoneMapper lcZoneMapper;
	
	public TLcZone findByKey(String code){
		return lcZoneMapper.findByKey(code);
	}
	
	public int add(TLcZone tlcMap) throws BussinessException{
		TLcZone dbTLcMap=lcZoneMapper.findByKey(tlcMap.getCode());
		if(dbTLcMap!=null)
			throw new BussinessException("编号已存在,不能重复添加");
		return lcZoneMapper.add(tlcMap);
	}
    
	public int update(TLcZone tlcMap) throws BussinessException{
		return update(tlcMap, false);
	}
	
	public int update(TLcZone tlcMap,boolean bQuery) throws BussinessException{
		if(bQuery){
			TLcZone dbTLcMap=lcZoneMapper.findByKey(tlcMap.getCode());
			if(dbTLcMap==null)
				throw new BussinessException("编号不存在,不能更新");
		}
		return lcZoneMapper.update(tlcMap);
	}
	
	public int deleteByKey(String code){
		return lcZoneMapper.deleteByKey(code);
	}
	
	public long getCount(TLcZone tlcMap){
		return lcZoneMapper.getCount(tlcMap);
	}
	
	public List<TLcZone> getListModel(TLcZone tlcMap){
		return lcZoneMapper.getListModel(tlcMap);
	}	
}
