package com.joker.core.cache;

public interface Cache {

    public static final Integer DEFAULT_CACHE_TIME = 5;	//5分钟

    void put(String key, Object value);

    void put(String key, Object value, int expirMins);

    Object get(String key);

    void remove(String key);

    void clear();
}
