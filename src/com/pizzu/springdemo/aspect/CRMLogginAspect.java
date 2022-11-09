package com.pizzu.springdemo.aspect;


import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLogginAspect {

	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());
	// setup pointcut declarations
	
	@Pointcut("execution(* com.pizzu.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.pizzu.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.pizzu.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	// add @before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinpoint) {
		
		// display method we are calling
		String theMethodString = theJoinpoint.getSignature().toShortString();
		logger.info("=====>> in @Before: calling method: " + theMethodString);
		// display the arguments to the method
		
		// get the arguments
		
		Object[] args = theJoinpoint.getArgs();
		
		for (Object tempArgs: args) {
			logger.info("===>> argument: "+ tempArgs);
		}
	}

	// add @afterReturning advice
	
	@AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		String theMethodString = theJoinPoint.getSignature().toShortString();
		logger.info("=====>> in @AfterReturning: calling method: " + theMethodString);
		
		logger.info("===>> result: "+ theResult);
	}
}
