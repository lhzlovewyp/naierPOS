/**
 * 
 */
package com.joker.core.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * 
 * ehcache实现类，可以指定不同的ehcache对象
 * 
 * @author lvhaizhen
 *
 */
public class EHCacheImpl  implements Cache{

	private Ehcache ehCache;
	
	public EHCacheImpl(Ehcache ehCache){
		this.ehCache=ehCache;
	}

	@Override
	public void put(String key, Object value) {
		put(key,value,DEFAULT_CACHE_TIME);
	}

	@Override
	public void put(String key, Object value, int expirMins) {
		ehCache.put(new Element(key,value,false,expirMins*60,expirMins*60));
	}

	@Override
	public Object get(String key) {
		Element ele=ehCache.get(key);
		return ele == null ? null : ele.getObjectValue();
	}

	@Override
	public void remove(String key) {
		ehCache.remove(key);
	}

	@Override
	public void clear() {
		ehCache.removeAll();
	}

	public Ehcache getEhCache() {
		return ehCache;
	}

	public void setEhCache(Ehcache ehCache) {
		this.ehCache = ehCache;
	}
	
	

}
