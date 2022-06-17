package com.user.registration.exceptionhandling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	Log logger = LogFactory.getLog(this.getClass());
	
	@AfterThrowing(pointcut = "execution(* com.user.registration.service.*.*(..))", throwing = "exception")
	public void afterThrowing(Exception exception) {
		logger.error(exception.getMessage());
	}
}
