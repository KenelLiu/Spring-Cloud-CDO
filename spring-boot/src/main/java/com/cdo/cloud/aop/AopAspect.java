package com.cdo.cloud.aop;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(0)
public class AopAspect {
	private static Log logger=LogFactory.getLog(AopAspect.class);
		
	@Pointcut("within(com.cdo.cloud.web.*)") 
	public void webLayer(){		
		logger.info(".........Pointcut[webLayer]........");
	}
	
	
	@Before(value="webLayer()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = attributes.getRequest();   
		logger.info(".........AopAspect doBefore Pointcut[webLayer]........."); //切面step.2
	  }
	
	@AfterReturning(pointcut="webLayer()",returning="returnValue")
    public void doAfterReturning(JoinPoint point,Object returnValue) {		
		logger.info(".........AopAspect AfterReturning Pointcut[webLayer].........returnValue="+returnValue);//切面step.8
	  }	

   @After(value = "webLayer()")  
   public void doAfter() {  
	   logger.info(".........AopAspect doAfter() Pointcut[webLayer]........."); //切面step.9
   }  	
    
   @Around(value="webLayer()")  
   public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {  
		  logger.info(".........AopAspect  doAround before pjp.proceed() Pointcut[webLayer]........."); //切面step.1
	      Object obj = joinPoint.proceed();  
		  logger.info(".........AopAspect  doAround after pjp.proceed() Pointcut[webLayer]........."); //切面step.10
	      return obj;  
	  }  
	    
   
   @AfterThrowing(value="webLayer()",throwing = "e")  
   public void doAfterThrowing(Exception e) {  
	   logger.error(e.getMessage(),e);  
   }  
   


}
