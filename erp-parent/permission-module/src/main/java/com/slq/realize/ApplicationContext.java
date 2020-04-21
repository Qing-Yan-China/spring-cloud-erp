package com.slq.realize;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ApplicationContext implements ApplicationContextAware{

	private static org.springframework.context.ApplicationContext applicationContext; 
	@Override
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)
			throws BeansException {
		
		ApplicationContext.applicationContext=applicationContext;
	}
	public static org.springframework.context.ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	

}
