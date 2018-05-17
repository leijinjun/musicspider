package cn.person.musicspider.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext ac;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}
	
	public static ApplicationContext getAC(){
		return ac;
	}

	public static Object getBean(String beanName){
		return ac.getBean(beanName);
	}
}
