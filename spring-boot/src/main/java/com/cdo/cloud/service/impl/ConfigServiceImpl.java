package com.cdo.cloud.service.impl;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.cdo.cloud.dao.TVDao;
import com.cdo.cloud.mapper.write.ConfigMapper;
import com.cdo.cloud.service.ConfigService;

@Service(value="ConfigService")
public class ConfigServiceImpl implements ConfigService {
	@Resource
	private TVDao tvDao;
	@Resource
	ConfigMapper configMapper;
	@Override
	public void save(Integer baseId) {
//		tvDao.delete(baseId);
		configMapper.save(baseId);
		throw new RuntimeException("ConfigService RuntimeException........");
		
	}

}
