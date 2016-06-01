package com.joker.common.test;

/**
 * Created by chenqi on 16/4/25.
 */
/*public class Singleton {
    private static Singleton singleton = new Singleton();

    public static Singleton getInstance(){
        return singleton;
    }
}*/

public class Singleton{
    private static Singleton singleton = null;

    public synchronized static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
