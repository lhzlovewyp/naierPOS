package com.joker.core.servlet;

import com.joker.core.util.PropertiesUtil;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenqi on 16/5/4.
 */
public class ThreadPool {

    private static ThreadPoolExecutor threadPool = null;

    private ThreadPool(){

    }

    public static void initPool(){
        Properties props = PropertiesUtil.loadProperties("jdbc.properties");
        int core_pool_size = Integer.parseInt(props.getProperty("task.core_pool_size"));
        int max_pool_size = Integer.parseInt(props.getProperty("task.max_pool_size"));
        int queue_capacity = Integer.parseInt(props.getProperty("task.queue_capacity"));
        long keep_alive_seconds = Long.parseLong(props.getProperty("task.keep_alive_seconds"));

        threadPool = new ThreadPoolExecutor(core_pool_size, max_pool_size, keep_alive_seconds, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queue_capacity));

    }

    public static ThreadPoolExecutor getThreadPool(){
        return threadPool;
    }

    public static void destory(){
        threadPool.shutdown();
    }

    public static void destoryNow(){
        threadPool.shutdownNow();
    }
}
