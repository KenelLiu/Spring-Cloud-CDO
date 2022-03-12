package com.cdo.cloud.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.cdo.cloud.dataSource.DataSourceFactory;

@Configuration
@AutoConfigureAfter(SpringTransactionManager.class)
public class WriteInterceptor {
	//==============local 事务管理器===========//
	@Resource(name="txWriteManager")
	private TransactionManager transactionManager;
	//=================事务拦截器===============//
	static final String TX_INTERCEPTOR_NAME="txWriteInterceptor";	
	/**
	 * 可传播事务配置
	 */
	private static final String[] DEFAULT_Required_TransactionAttributes= {
		"add*" ,
		"save*" ,
		"insert*" ,
		"delete*" ,
		"update*" ,
		"edit*" ,
		"batch*" ,
		"create*" ,
		"remove*" ,
	};	
	/**
	 * 默认的只读事务
	 */
	private static final String[] DEFAULT_ReadOnly_TransactionAttributes= {
		"get*" ,
		"find*" ,
		"query*" ,
		"select*" ,
		"list*" ,
		"*" ,
	};
	/**
	 * 自定义方法名的事务属性相关联,可以使用通配符(*)字符关联相同的事务属性的设置方法; 只读事务
	 */
	private  final static  String[] CUSTOM_ReadOnly_TransactionAttributes= {};
	/**
	 * 自定义方法名的事务属性相关联,可以使用通配符(*)字符关联相同的事务属性的设置方法;
	 * 传播事务(默认的){@link org.springframework.transaction.annotation.Propagation#REQUIRED}
	 */
	private final static String[] CUSTOM_Required_TransactionAttributes= {};
	
	/**
	 * 配置事务拦截器
	 */
	@Bean(TX_INTERCEPTOR_NAME)
	public TransactionInterceptor customTransactionInterceptor() {
		NameMatchTransactionAttributeSource transactionAttributeSource = new NameMatchTransactionAttributeSource();
		RuleBasedTransactionAttribute       readOnly = this.readOnlyTransactionRule();
		RuleBasedTransactionAttribute       required = this.requiredTransactionRule();
		// 默认的只读事务配置
		for (String methodName : DEFAULT_ReadOnly_TransactionAttributes) {
			transactionAttributeSource.addTransactionalMethod( methodName , readOnly );
		}
		// 默认的传播事务配置
		for (String methodName : DEFAULT_Required_TransactionAttributes) {
			transactionAttributeSource.addTransactionalMethod( methodName , required );
		}
		// 定制的只读事务配置
		for ( String methodName : CUSTOM_ReadOnly_TransactionAttributes ) {
			transactionAttributeSource.addTransactionalMethod( methodName , readOnly );
		}
		// 定制的传播事务配置
		for ( String methodName : CUSTOM_Required_TransactionAttributes ) {
			transactionAttributeSource.addTransactionalMethod( methodName , required );
		}
		return  new TransactionInterceptor(transactionManager , transactionAttributeSource);
	}
	/**
	 * 支持当前事务;如果不存在创建一个新的
	 * {@link org.springframework.transaction.annotation.Propagation#REQUIRED}
	 */
	private RuleBasedTransactionAttribute requiredTransactionRule () {
		RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
		required.setRollbackRules( Collections.singletonList( new RollbackRuleAttribute( Exception.class ) ) );
		required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		required.setTimeout( TransactionDefinition.TIMEOUT_DEFAULT );
		required.setTimeout(30);
//		required.setName(name);
		return required;
	}

	/**
	 * 只读事务
	 * {@link org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED}
	 */
	private RuleBasedTransactionAttribute readOnlyTransactionRule() {
		RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
		readOnly.setReadOnly( true );
		readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
		readOnly.setTimeout(30);
		return readOnly;
	}	
}
