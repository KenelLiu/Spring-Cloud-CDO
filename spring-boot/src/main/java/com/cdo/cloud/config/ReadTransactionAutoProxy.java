package com.cdo.cloud.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
@AutoConfigureAfter(ReadInterceptor.class) 
public class ReadTransactionAutoProxy {

	/**
	 * 默认只对 "*Service" , "*ServiceImpl" Bean 进行事务处理,"*"表示模糊匹配, 比如 : userService,orderServiceImpl
	 */
	private static final String[] DEFAULT_TRANSACTION_BeanNames={ "*Service" , "*ServiceImpl" };	
	/**
	 * 自定义事务 BeanName 拦截
	 */
	private   final static String[]  CUSTOM_TRANSACTION_BeanNames= {};
	/**
	 * 定义AOP拦截
	 */
	private static final String AOP_POINTCUT_EXPRESSION="execution(public * com.cloud.service.read.*.*(..))";
	
	@Resource(name=ReadInterceptor.TX_INTERCEPTOR_NAME)
	private TransactionInterceptor txAdvice;			
	/**
	 * 事务自动代理BeanNameAutoProxyCreator,配置事务拦截
	 * <p>
	 * {@link #customizeTransactionInterceptor(PlatformTransactionManager)}
	 */
	//@Bean
	public BeanNameAutoProxyCreator readBeanNameAutoProxyCreator() {
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		// 设置定制的事务拦截器
		beanNameAutoProxyCreator.setInterceptorNames(ReadInterceptor.TX_INTERCEPTOR_NAME);
		List< String > transactionBeanNames = new ArrayList<>( DEFAULT_TRANSACTION_BeanNames.length + CUSTOM_TRANSACTION_BeanNames.length );
		// 默认
		transactionBeanNames.addAll(Arrays.asList(DEFAULT_TRANSACTION_BeanNames ) );
		// 定制
		transactionBeanNames.addAll(Arrays.asList(CUSTOM_TRANSACTION_BeanNames) );
		// 归集
		beanNameAutoProxyCreator.setBeanNames(transactionBeanNames.toArray(new String[transactionBeanNames.size()]));

		beanNameAutoProxyCreator.setProxyTargetClass( true );
		return beanNameAutoProxyCreator;
	}
	
	@Bean
	public DefaultAdvisorAutoProxyCreator readAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator  advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setAdvisorBeanNamePrefix("com.cdo.cloud.service.read");
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}	

	@Bean
	public DefaultPointcutAdvisor readPointcutAdvisor(){
		DefaultPointcutAdvisor pointAdvisor=new DefaultPointcutAdvisor();
		pointAdvisor.setAdvice(txAdvice);
		AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		pointAdvisor.setPointcut(pointcut);		
		return pointAdvisor;
	}
	
}
