package com.cdo.cloud.service;

import com.cdo.cloud.entity.TLcZone;
/**
 * 使用beeanName配置自动代理事务,不再手工配置@Transactional
 * {@link com.iot.bodyiot.config.WriteTransactionAutoProxy#writeBeanNameAutoProxyCreator}
 * @Transactional(rollbackFor = Exception.class)
 * @author Kenel
 *
 */
public interface LCZoneService extends BaseSevice<TLcZone>{

}
