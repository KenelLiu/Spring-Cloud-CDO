package com.cdo.cloud.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.cdo.cloud.dao.TVDao;
import com.cdo.cloud.mapper.write.ConfigMapper;
import com.cdo.cloud.service.ConfigService;
import com.cdo.cloud.service.TVService;
@Service(value="TVService")
public class TVServiceImpl implements TVService {
	@Resource
	private TVDao tvDao;

	@Resource(name="ConfigService")
	private ConfigService configService;
	@Resource
	ConfigMapper configMapper;

	public void save(Integer baseId){
//		tvDao.save(baseId);
		configMapper.save(baseId);
//		baseId=baseId+1;
//		configService.save(baseId);
		tvDao.delete(baseId);
	}
    
}
