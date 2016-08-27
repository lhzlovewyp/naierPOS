/**
 * 
 */
package com.joker.core.cache;

import com.joker.core.util.SpringBeanFactory;

import net.sf.ehcache.Ehcache;


/**
 * @author lvhaizhen
 *
 */
public class CacheFactory {

	
	private static Cache ehCache;
	static {
		Ehcache globalEHcache=(Ehcache)SpringBeanFactory.getBean("globalEHcache");
		ehCache=new EHCacheImpl(globalEHcache);
	}
	
	public static Cache getCache(){
		return ehCache;
	}
	
}
