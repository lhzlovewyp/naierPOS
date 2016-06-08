package com.joker.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringBeanFactory implements BeanFactoryAware {
	
	private static BeanFactory ctx;
	
	@Override
	public void setBeanFactory(BeanFactory beanFactoryArg) throws BeansException {
		ctx=beanFactoryArg;
	}

	/**
	 * 从spring配置文件中,获取id对应的对象
	 * @param beanName  查找的bean的id
	 * 
	 * @return beanName 对应的对象
	 */
	public static Object getBean(String beanName){
		return ctx.getBean(beanName);
	}
	
	public static boolean containsBean(String beanName) {
		return ctx.containsBean(beanName);
	}
}