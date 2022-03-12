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
@Order(1)
public class AopAspect2 {
	private static Log logger=LogFactory.getLog(AopAspect2.class);
	
	@Pointcut("within(com.cdo.cloud.web.*)") 
	public void pointCut(){
		   logger.info(".........AopAspect2 pointCut........."); 
	}
	
	@Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容  
        logger.info(".........AopAspect2 doBefore pointCut........."); //切面step.4
	  }
	
	@AfterReturning(pointcut="pointCut()",returning="returnValue")
    public void doAfterReturning(JoinPoint joinPoint,Object returnValue) {
        // 记录下请求内容  														//切面step.4 -切面step.5 之间 为具体action处理
        logger.info(".........AopAspect2 doAfterReturning pointCut.........returnValue="+returnValue); //切面step.5
        
	  }	

   //声明最终通知  
   @After("pointCut()")  
   public void doAfter() {  
	    logger.info(".........AopAspect2  doAfter()	pointCut........");//切面step.6
   }  
   
  @Around("pointCut()")  
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{  
	    logger.info(".........AopAspect2 doAround before pjp.proceed() pointCut........");//切面step.3
	    /**
	    logger.info(".........AopAspect2 doAround kind:" + joinPoint.getKind());
	    logger.info(".........AopAspect2 doAround 目标方法名为:" + joinPoint.getSignature().getName());
	    logger.info(".........AopAspect2 doAround 目标方法所属类的简单类名:" +joinPoint.getSignature().getDeclaringType().getSimpleName());
	    logger.info(".........AopAspect2 doAround 目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
	    logger.info(".........AopAspect2 doAround 目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
      	//获取传入目标方法的参数
      	Object[] args = joinPoint.getArgs();
      	for (int i = 0; i < args.length; i++) {
    	  logger.info(".........AopAspect2 doAround 第" + (i+1) + "个参数为:" + args[i]);
      	}
      	logger.info(".........AopAspect2 doAround 被代理的对象:" + joinPoint.getTarget());
      	logger.info(".........AopAspect2 doAround 代理对象自己:" + joinPoint.getThis());
      	Object obj = joinPoint.proceed(new Object[]{"1","{\"point\":\"cut\"}"});
      	**/
	  Object[] args = joinPoint.getArgs();  
      Object obj = joinPoint.proceed(args);     
      logger.info(".........AopAspect2 doAround after pjp.proceed() pointCut........");  //切面step.7
      return obj;  
  }  
  
  @AfterThrowing(pointcut = "pointCut()", throwing = "e")  
  public void doAfterThrowing(Exception e) {  
	   logger.error(e.getMessage(),e);  
  }    
}
