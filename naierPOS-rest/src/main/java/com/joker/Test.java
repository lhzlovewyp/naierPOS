package com.joker;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		CacheManager ehCacheManager = new CacheManager();
		Ehcache cache = ehCacheManager.getCache("globalEHcache");
		Element e = new Element("aa", "aa", false, 500, 500);
		cache.put(e);
		System.out.println(cache.get("aa"));
		Thread.sleep(1050);
		System.out.println(cache.get("aa"));
	}

}
