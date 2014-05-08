package org.danisoft.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ServiceLocator implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		CONTEXT = context;
	}

	public static <T> T getSingle(Class<T> clazz) {
		return CONTEXT.getBean(clazz);
	}
}
