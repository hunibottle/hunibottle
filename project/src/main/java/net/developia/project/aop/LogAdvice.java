package net.developia.project.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import net.developia.project.aop.LogAdvice;

@Log4j
@Aspect
@Component
public class LogAdvice {
	
	@AfterThrowing(
	pointcut = "execution(* net.developia.project.service.BoardService*.*(..))",
	throwing = "exception")
	public void logException(Exception exception) throws Exception{
		log.info("LogAdvice.logException 수행중... ");
		log.info(exception.toString());
		throw exception;
	}

}
